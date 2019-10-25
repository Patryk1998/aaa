package com.zwirownia.services;


import com.zwirownia.dao.KlientDao;
import com.zwirownia.entities.*;
import com.zwirownia.entities.dto.CennikCurrencyDto;
import com.zwirownia.entities.dto.CennikDto;
import com.zwirownia.entities.dto.KlientDto;
import com.zwirownia.entities.dto.nbpApi.WalutaResponseDto;
import com.zwirownia.nbp.NbpClient;
import com.zwirownia.support.Mapper;
import com.zwirownia.support.ModelConst;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class KlientService implements IService<Klient, KlientDto>  {

    @Autowired
    private KlientDao klientDao;

    @Autowired
    private SamochodyService samochodyService;

    @Autowired
    private ModelConst modelConst;

    @Autowired
    private Mapper mapper;

    @Autowired
    private CenyService cenyService;

    @Autowired
    private NbpClient nbpClient;

    public List<Klient> getAll() {
        return StreamSupport.stream(klientDao.findAll().spliterator(), false).collect(Collectors.toList());
    }

    public Klient getById(Long id) {
        return klientDao.findById(id).get();
    }

    public Klient getByNazwa(String nazwa) {
        return klientDao.findByNazwa(nazwa);
    }

    public List<String> getNazwy() {
        return getAll().stream()
                .map(klient -> klient.getNazwa())
                .collect(Collectors.toList());
    }

    public void add(KlientDto klientDto) {
        klientDao.save(mapper.klientDtoToKlient(klientDto));
        modelConst.setKlienci(getAll());
        modelConst.setNazwyKlientow(getNazwy());
    }

    public Set<Samochod> getSamochodsOfKlient(Long id) {
        return getById(id).getSamochody();
    }

    public void deleteSamochodOfKlient(Long samochodId, Long klientId) {
        Klient klient = getById(klientId);
        Samochod samochod = samochodyService.getById(samochodId);
        samochod.getKlienci().remove(klient);
        klient.getSamochody().remove(samochod);
        samochodyService.samochodDao.save(samochod);
    }

    public void addSamochodOfKlient(String samochodReje, Long klientId) {
        Klient klient = getById(klientId);
        Samochod samochod = samochodyService.getByRejestracja(samochodReje);
        samochod.addKlient(klient);
        klient.addSamochod(samochod);
        samochodyService.samochodDao.save(samochod);
    }

    public void update(KlientDto klientDto, Long id) {
        Klient klient = getById(id);
        klientDao.save(updateComparison(klient, klientDto));
        modelConst.setKlienci(getAll());
        modelConst.setNazwyKlientow(getNazwy());
        modelConst.refreshWuzetki();
    }

    private Klient updateComparison(Klient klient, KlientDto klientDto) {
        String nazwaNew = klientDto.getNazwa();
        if(!klient.getNazwa().equals(nazwaNew)) klient.setNazwa(nazwaNew);
        return klient;
    }

    public List<Wuzetka> getWuzetkiOfKlient(String klientName) {
        return new ArrayList<>(getByNazwa(klientName).getWuzetki());
    }

    public List<CennikCurrencyDto> getCenyOfKlient(Long klientId) {
        return mapper.cennikToCennikDto(cenyService.getAllOfKlient(getById(klientId)), Double.parseDouble(getCurrency().getRates().get(0).getValue()));
    }

    public List<Klient> getNotAttachedToSamochod(Samochod samochod) {
        Set<Long> kliecniOfSamochodIds = samochod.getKlienci().stream()
                .map(Klient::getId)
                .collect(Collectors.toSet());
        List<Klient> filteredKlienci = getAll().stream()
                .filter(klient -> !kliecniOfSamochodIds.contains(klient.getId()))
                .collect(Collectors.toList());
        return filteredKlienci;
    }

    public List<Samochod> getSamochodyNotAttachedToKlient(Long klientId) {
        return samochodyService.getNotAttachedToKlient(getById(klientId));
    }

    public WalutaResponseDto getCurrency() {
        return nbpClient.getCurrency();
    }
}

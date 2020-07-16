package com.fight.covid.helper;

import com.fight.covid.model.Countries;
import com.fight.covid.model.ListViewModel;
import com.fight.covid.model.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FightCovidHelper {
    public List<ListViewModel> getListViewModel(Response response){
        if(null == response)
            return null;
        List<ListViewModel> mapList =new ArrayList<>();
        for(Map.Entry<String,Countries> arg:response.getCountries().entrySet()){
           ListViewModel listViewModel = new ListViewModel();
           listViewModel.setCountryCode(arg.getKey());
           listViewModel.setCountryFlagUrl(arg.getValue().getFlag());
           listViewModel.setCountryName(arg.getValue().getName());
           mapList.add(listViewModel);

        }
        return mapList;
    }
}

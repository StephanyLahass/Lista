package lahass.stephany.lista.model;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

//Vai guardar os dados da MainActivity
public class MainActivityViewModel extends ViewModel {
    //guarda a lista de itens cadastrados
    List<MyItem> itens = new ArrayList<>();

    //obtem a lista de itens atraves do getter
    public List<MyItem> getItens() {
        return itens;
    }
}


package lahass.stephany.lista.model;

import android.net.Uri;

import androidx.lifecycle.ViewModel;

//vai guardar os dados referentes a NewItemActivity
public class NewItemActivityViewModel extends ViewModel {
    //guarda o endereço URI da foto escolhida pelo usuário
    Uri selectPhotoLocation = null;

    //obtem a lista de itens atraves do getter
    public Uri getSelectPhotoLocation() {
        return selectPhotoLocation;
    }

    // seta o endereço URI dentro do ViewMode
    public void setSelectPhotoLocation(Uri selectPhotoLocation) {
        this.selectPhotoLocation = selectPhotoLocation;
    }
}


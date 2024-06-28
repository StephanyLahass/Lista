package lahass.stephany.lista.activity;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import lahass.stephany.lista.R;
import lahass.stephany.lista.adapter.MyAdapter;
import lahass.stephany.lista.model.MainActivityViewModel;
import lahass.stephany.lista.model.MyItem;
import lahass.stephany.lista.util.Util;

public class MainActivity extends AppCompatActivity {
    static int NEW_ITEM_REQUEST =1;
    MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        RecyclerView rvItens = findViewById(R.id.rvItens);
        MainActivityViewModel vm = new ViewModelProvider( this ).get( MainActivityViewModel.class );
        List<MyItem> itens = vm.getItens();
        myAdapter = new MyAdapter(this, itens);
        rvItens.setAdapter(myAdapter);

        //indica que não há variação de tamanho entre os itens da lista
        rvItens.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager;
        layoutManager = new LinearLayoutManager(this);
        rvItens.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvItens.getContext(),
                DividerItemDecoration.VERTICAL);
        rvItens.addItemDecoration(dividerItemDecoration);

        //aqui comeca o botao FAB
        FloatingActionButton fabAddItem = findViewById(R.id.fabAddNewItem);
        //registra o ouvidor de clicks
        fabAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            //navega atraves do intent para o NewItemActivity
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,NewItemActivity.class);
                //executa o intent
                startActivityForResult(i, NEW_ITEM_REQUEST);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Esse pedaço verifica se as condições de retorno foram cumpridas e se sim, cria uma instância para guardar os dados do item.
        //obtem os dados retornados e os guarda, adiciona o item a uma lista de itens.
        if (requestCode == NEW_ITEM_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                MyItem myItem = new MyItem();
                myItem.title = data.getStringExtra("title");
                myItem.description = data.getStringExtra("description");
                Uri selectedPhotoURI = data.getData();

                try {
                    //carrega a imagem e a guarda dentro de um bitmap
                    Bitmap photo = Util.getBitmap(MainActivity.this, selectedPhotoURI, 100, 100);
                    //guarda o bitmap da imagem dentro de um objeto
                    myItem.photo = photo;
                }
                // a exceção é disparada caso o arquivo de imagem não seja encontrado
                catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                //obtem a lista de itens
                MainActivityViewModel vm = new ViewModelProvider(this).get(MainActivityViewModel.class);
                //guarda o novo item
                List<MyItem> itens = vm.getItens();

                itens.add(myItem);
                myAdapter.notifyItemInserted(itens.size()-1);
            }
        }
    }
}

package com.kylukz.fooddev.View;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aide.aiDelivery.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.kylukz.fooddev.Adapters.EmpresaAdapter;
import com.kylukz.fooddev.Animation.Animatoo;
import com.kylukz.fooddev.DAO.EmpresaDAO;
import com.kylukz.fooddev.DAO.UsuarioDAO;
import com.kylukz.fooddev.JavaBeans.EntidadeCadastroUsuario;
import com.kylukz.fooddev.JavaBeans.EntidadeEmpresa;
import com.google.android.material.navigation.NavigationView;
import com.kylukz.fooddev.Toolbox.Ferramentas;
import com.kylukz.fooddev.Toolbox.MenusAbaLateralEsquerda;
import com.kylukz.fooddev.Valid.ValidadorCadastro;

import java.util.ArrayList;
import java.util.List;

public class MenuPrincipalEmpresa extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, LocationListener {


    public static Context CTX;
    private List<EntidadeEmpresa> ponto = new ArrayList<>();
    // Entidade
    final EntidadeCadastroUsuario entidadeCadastroUsario = new EntidadeCadastroUsuario();
    UsuarioDAO usuarioDAO = new UsuarioDAO();

    boolean primeiraVez = false;
    boolean seCompletouCadastro = false; // Se Completou Cadastro
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE); // remove titulo
        setContentView(R.layout.activity_menu_principal);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Se o usuário completou o cadastro
        seCompletouCadastro = UsuarioDAO.ENTIDADE_USUARIO.isSeCompletouCadastro();

        try {
            //////////////////////////////// FIREBASE TOKEN ////////////////////////////////
            FirebaseInstanceId.getInstance().getInstanceId()
                    .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                        @Override
                        public void onComplete(@NonNull Task<InstanceIdResult> task) {
                            if (!task.isSuccessful()) {
                                return;
                            }
                            usuarioDAO.setTokenFirebasePushUsuario(task.getResult().getToken());
                        }
                    });
            //////////////////////////////////////////////////////////////////////////////
        } catch (Exception e) {

        }


        try {
            if (Build.VERSION.SDK_INT >= 23) {
                String[] PERMISSIONS = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
                ActivityCompat.requestPermissions((Activity) MenuPrincipalEmpresa.this, PERMISSIONS, 2);
            }
            mgr = (LocationManager) getSystemService(LOCATION_SERVICE);
            dumpProviders();
            Criteria criteria = new Criteria();
            best = mgr.getBestProvider(criteria, true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
            }
            Location location = mgr.getLastKnownLocation(best);
            dumpLocation(location);


            System.err.println("GPS ===> " + location.getLatitude() + ", " + location.getLongitude());

            solicitaGeoLocalizacao();
        } catch (Exception e) {
            System.err.println(e);
        }


        // Animação carregamento de tela
        Animatoo.animateSwipeRight(this);

        // Carrega  a lista de empresas (pontos)
        new AsyncTaskGetEmpresa().execute();
        // Context de uso global
        CTX = MenuPrincipalEmpresa.this;

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(MenuPrincipalEmpresa.this);

        // Campo de pesquisa por nome da empresa
        final SearchView searchViewPesquisaEmpresa = (SearchView) findViewById(R.id.searchViewPesquisaEmpresa);
        searchViewPesquisaEmpresa.setIconified(true);
        // Muda cor do search
        SearchView.SearchAutoComplete searchAutoComplete = (SearchView.SearchAutoComplete)searchViewPesquisaEmpresa.findViewById(R.id.search_src_text);
        searchAutoComplete.setHintTextColor(Color.WHITE);

        searchViewPesquisaEmpresa.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                if (primeiraVez && seCompletouCadastro) {
                    pesq = "";
                    new AsyncTaskGetEmpresa().execute();
                    primeiraVez = false;
                }
                primeiraVez = true;
                return false;
            }
        });

        searchViewPesquisaEmpresa.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    pesq = searchViewPesquisaEmpresa.getQuery().toString();
                    new AsyncTaskGetEmpresa().execute();
                }

            }
        });


        searchViewPesquisaEmpresa.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextChange(String newText) {
                // TODO Auto-generated method stub
                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                /**
                 * hides and then unhides search tab to make sure keyboard disappears when query is submitted
                 */
                searchViewPesquisaEmpresa.setVisibility(View.INVISIBLE);
                searchViewPesquisaEmpresa.setVisibility(View.VISIBLE);
                return false;
            }

        });

        LinearLayout buttonBotaoPesquisarEmpresa = (LinearLayout) findViewById(R.id.buttonBotaoPesquisarEmpresa);
        buttonBotaoPesquisarEmpresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (searchViewPesquisaEmpresa.getQuery().toString().length() > 0) {
                    pesq = searchViewPesquisaEmpresa.getQuery().toString();
                    new AsyncTaskGetEmpresa().execute();
                }
            }
        });

        ////////////////////////////////////
        //       COMPLETAR CADASTRO       //
        ////////////////////////////////////
        final EditText editTextCampoCEPCompletarCadastro = (EditText) findViewById(R.id.editTextCampoCEPCompletarCadastro);
        final EditText editTextCampoEnderecoCompletarCadastro = (EditText) findViewById(R.id.editTextCampoEnderecoCompletarCadastro);
        final EditText editTextCampoNumero = (EditText) findViewById(R.id.editTextCampoNumero);
        final EditText editTextCampoBairro = (EditText) findViewById(R.id.editTextCampoBairro);
        final EditText editTextCampoCidade = (EditText) findViewById(R.id.editTextCampoCidade);
        final EditText editTextCampoReferencia = (EditText) findViewById(R.id.editTextCampoReferencia);
        // RequestFocus para eficiência
        editTextCampoCEPCompletarCadastro.requestFocus();
        // Botão completar
        Button buttonBotaoCompletarCadastro = (Button) findViewById(R.id.buttonBotaoCompletarCadastro);
        buttonBotaoCompletarCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    // permissão
                    ActivityCompat.requestPermissions(MenuPrincipalEmpresa.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);


                    if (setAlertaGPSOnOff()) {
                        // Get geo
                        Object[] geo = solicitaGeoLocalizacao();

                        // Validação
                        ValidadorCadastro validaFormularioCadastroPinPorCPF = new ValidadorCadastro();
                        // Carrega entidade
                        entidadeCadastroUsario.setCep(editTextCampoCEPCompletarCadastro.getText().toString());
                        entidadeCadastroUsario.setEndereco(editTextCampoEnderecoCompletarCadastro.getText().toString());
                        entidadeCadastroUsario.setNumero(editTextCampoNumero.getText().toString());
                        entidadeCadastroUsario.setBairro(editTextCampoBairro.getText().toString());
                        entidadeCadastroUsario.setCidade(editTextCampoCidade.getText().toString());
                        entidadeCadastroUsario.setLatitude(geo[0].toString());
                        entidadeCadastroUsario.setLongitude(geo[1].toString());
                        entidadeCadastroUsario.setReferencia(editTextCampoReferencia.getText().toString());

                        // Set validar
                        Object[] valida = null;
                        valida = validaFormularioCadastroPinPorCPF.setValidaFormularioCompletarCadastro(entidadeCadastroUsario);
                        // CEP
                        if (!Boolean.parseBoolean(String.valueOf(valida[0])) && Integer.parseInt(String.valueOf(valida[2])) == 0) {
                            System.out.println("====================> 1");
                            editTextCampoCEPCompletarCadastro.setError(valida[1] + "");
                            return;
                        }
                        // Endereco
                        if (!Boolean.parseBoolean(String.valueOf(valida[0])) && Integer.parseInt(String.valueOf(valida[2])) == 1) {
                            System.out.println("====================> 2");
                            editTextCampoEnderecoCompletarCadastro.setError(valida[1] + "");
                            return;
                        }
                        // nº
                        if (!Boolean.parseBoolean(String.valueOf(valida[0])) && Integer.parseInt(String.valueOf(valida[2])) == 2) {
                            System.out.println("====================> 3");
                            editTextCampoNumero.setError(valida[1] + "");
                            return;
                        }
                        // Bairro
                        if (!Boolean.parseBoolean(String.valueOf(valida[0])) && Integer.parseInt(String.valueOf(valida[2])) == 3) {
                            System.out.println("====================> 4");
                            editTextCampoBairro.setError(valida[1] + "");
                            return;
                        }
                        // Cidade
                        if (!Boolean.parseBoolean(String.valueOf(valida[0])) && Integer.parseInt(String.valueOf(valida[2])) == 4) {
                            System.out.println("====================> 5");
                            editTextCampoCidade.setError(valida[1] + "");
                            return;
                        }

                        // DAO
                        new AsyncTaskSetCompletarCadastro().execute(entidadeCadastroUsario);
                    }
                } catch (Exception e) {
                    System.out.println("====================> 5" + e);
                }
            }
        });

        // Campo endereço por CEP
        editTextCampoCEPCompletarCadastro.setOnKeyListener(new View.OnKeyListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                try {
                    System.out.println("======== asdjhaiudhasiudasd ");
                    if (editTextCampoCEPCompletarCadastro.getText().toString().length() == 8) {
                        String[] dadosRuaCEP = Ferramentas.getEnderecoPorCEP(editTextCampoCEPCompletarCadastro.getText().toString());
                        System.out.println("====================> dadosRuaCEP " + dadosRuaCEP[1]);
                        editTextCampoEnderecoCompletarCadastro.setText(dadosRuaCEP[1]);
                        editTextCampoBairro.setText(dadosRuaCEP[3]);
                        editTextCampoCidade.setText(dadosRuaCEP[4]);
                        // Focus nº endereço
                        editTextCampoNumero.requestFocus();
                    }
                } catch (Exception e) {
                    System.out.println("====================> e" + e);
                }
                return false;
            }
        });


        // Se não completou o cadastro
        if (!seCompletouCadastro) {
            setExpandeColapsaBottomSheet(BottomSheetBehavior.STATE_EXPANDED, 0);
            View bottomSheet = findViewById(R.id.bottom_sheet_completar_cadastro_endereco);
            final BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
            // Campo botão campo pesquisa search view
            buttonBotaoPesquisarEmpresa.setFocusable(false);
            buttonBotaoPesquisarEmpresa.setEnabled(false);
            buttonBotaoPesquisarEmpresa.setClickable(false);
            buttonBotaoPesquisarEmpresa.setVisibility(View.GONE);
            // Disabilita Search View
            searchViewPesquisaEmpresa.setFocusable(false);
            searchViewPesquisaEmpresa.setEnabled(false);
            searchViewPesquisaEmpresa.setClickable(false);
            searchViewPesquisaEmpresa.setVisibility(View.GONE);
            // Desabilita recyclerview empresas
            RecyclerView recyclerViewsPontos = (RecyclerView) findViewById(R.id.recyclerViewsPontos);
            recyclerViewsPontos.setFocusable(false);
            recyclerViewsPontos.setEnabled(false);
            recyclerViewsPontos.setClickable(false);
            recyclerViewsPontos.setVisibility(View.GONE);

            behavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                @Override
                public void onStateChanged(@NonNull View bottomSheet, int newState) {
                    if (!seCompletouCadastro && (newState == BottomSheetBehavior.STATE_DRAGGING || newState == BottomSheetBehavior.STATE_COLLAPSED)) {
                        setExpandeColapsaBottomSheet(BottomSheetBehavior.STATE_EXPANDED, 0);
                    }
                }

                @Override
                public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                    //
                }
            });
        }


        // disabilita aba lateral esquerda
//        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
         drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    /*    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
*/
        // disabilita aba lateral esquerda
//        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_x, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sair) {
            MenusAbaLateralEsquerda.botao3Pontinhos(this);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
//            MenusAbaLateralEsquerda.botaoSuporte(MenuPrincipalEmpresa.this);
        } else if (id == R.id.nav_entregador) {
//            MenusAbaLateralEsquerda.botaoSuporte(MenuPrincipalEmpresa.this);
        } else if (id == R.id.nav_compartilhar) {
            MenusAbaLateralEsquerda.botaoIndiqueParaUmAmigo(MenuPrincipalEmpresa.this, this);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private boolean setAlertaGPSOnOff() {
        LocationManager locationManager = null;
        boolean gps_enabled = false;
        boolean network_enabled = false;
        if ( locationManager == null ) {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        }
        try {
            gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex){}
        try {
            network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex){}
        if ( !gps_enabled && !network_enabled ){
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setMessage("GPS está desligado, favor ligá-lo!");
            dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //this will navigate user to the device location settings screen
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);

                }
            });
            AlertDialog alert = dialog.create();
            alert.show();
        }
        return gps_enabled;

//        final boolean[] ligado = {false};
//        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
//        alertDialogBuilder.setMessage("O serviço de GPS é necessário e está desligado no momento, deseja ligá-lo?")
//                .setCancelable(false)
//                .setPositiveButton("Página de configurações",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                Intent callGPSSettingIntent = new Intent(
//                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                                startActivity(callGPSSettingIntent);
//                                ligado[0] = true;
//                            }
//                        });
//        alertDialogBuilder.setNegativeButton("Cancelar",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        ligado[0] = false;
//                        dialog.cancel();
//
//                    }
//                });
//        AlertDialog alert = alertDialogBuilder.create();
//        alert.show();
//        return ligado[0];
    }

    private int clicksBackPressed = 0;

    @Override
    public void onBackPressed() {
        String msg = "Aperte mais 3 vezes para deslogar!";
        if (clicksBackPressed == 2) {
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        }
        if (clicksBackPressed == 5) {
            this.finishAffinity();
        }
        clicksBackPressed++;
    }

    private BottomSheetBehavior mBottomSheetBehavior;

    /**
     * Exibe bottomsheet completar cadastro
     *
     * @author Igor Maximo <igormaximo_1989@hotmail.com>
     * @date 20/06/2020
     */
    public void setExibeColapsaCompletarCadastro(int bottomSheetAcao) {
        try {
            View bottomSheet = null;
            switch (bottomSheetAcao) {
                case 0:
                    bottomSheet = findViewById(R.id.bottom_sheet_completar_cadastro_endereco);
                    break;

            }
            mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
            switch (mBottomSheetBehavior.getState()) {
                case BottomSheetBehavior.STATE_COLLAPSED:
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    bottomSheet.requestLayout();
                    bottomSheet.invalidate();
                    break;
                case BottomSheetBehavior.STATE_DRAGGING:
                    break;
                case BottomSheetBehavior.STATE_EXPANDED:
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    bottomSheet.requestLayout();
                    bottomSheet.invalidate();
                    break;
                case BottomSheetBehavior.STATE_HIDDEN:
                    break;
                case BottomSheetBehavior.STATE_SETTLING:
                    break;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Altera o state da bottom sheet
     *
     * @author Igor Maximo <igormaximo_1989@hotmail.com>
     * @date 01/05/2020
     */
    private void setExpandeColapsaBottomSheet(int state, int bottomSheetAcao) {
        View bottomSheet = null;
        switch (bottomSheetAcao) {
            case 0:
                bottomSheet = findViewById(R.id.bottom_sheet_completar_cadastro_endereco);
                break;

        }
        bottomSheet.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                setExpandeColapsaBottomSheet(BottomSheetBehavior.STATE_EXPANDED, 0);
                return false;
            }
        });
        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        mBottomSheetBehavior.setState(state);
        bottomSheet.requestLayout();
        bottomSheet.invalidate();
    }

    /**
     * Carrega todos os os pontos na tela
     *
     * @uthor Igor Maximo <igormaximo_1989@hotmail.com>
     * @date 12/04/2020
     */
    private void setCarregarEmpresas(List<EntidadeEmpresa> empresa) {
        RecyclerView recyclerViewEmpresa = (RecyclerView) findViewById(R.id.recyclerViewsPontos);
        EmpresaAdapter empresaAdapter = new EmpresaAdapter(empresa);
        RecyclerView.LayoutManager mLayoutManager;
        mLayoutManager = new GridLayoutManager(this, 2);
        recyclerViewEmpresa.setLayoutManager(mLayoutManager);
        recyclerViewEmpresa.setAdapter(empresaAdapter);
        recyclerViewEmpresa.setItemAnimator(null);
        recyclerViewEmpresa.setHasFixedSize(false);
        recyclerViewEmpresa.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerViewEmpresa.setItemViewCacheSize(20);
        recyclerViewEmpresa.setDrawingCacheEnabled(true);
        recyclerViewEmpresa.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_LOW);
        recyclerViewEmpresa.setItemAnimator(null);
        recyclerViewEmpresa.setHasFixedSize(false);

        ViewCompat.setNestedScrollingEnabled(recyclerViewEmpresa, false);

        recyclerViewEmpresa.setNestedScrollingEnabled(false);

        final RecyclerView recyclerViewAddOnPreDrawListener = recyclerViewEmpresa;
        recyclerViewAddOnPreDrawListener.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                recyclerViewAddOnPreDrawListener.getViewTreeObserver().removeOnPreDrawListener(this);
                for (int i = 0; i < recyclerViewAddOnPreDrawListener.getChildCount(); i++) {
                    View v = recyclerViewAddOnPreDrawListener.getChildAt(i);
                    v.setAlpha(0.0f);
                    v.animate().alpha(2.5f)
                            .setDuration(1000)
                            .setStartDelay(i * 150)
                            .start();
                }
                return true;
            }
        });
        empresaAdapter.notifyDataSetChanged();
    }

    String pesq = "";

    @Override
    public void onLocationChanged(Location location) {
        dumpLocation(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    private class AsyncTaskGetEmpresa extends AsyncTask<Void, Integer, List<EntidadeEmpresa>> {
        private ProgressDialog mProgress = null;

        @Override
        protected void onProgressUpdate(Integer... progress) {
            mProgress.setProgress(progress[0]);
        }

        @Override
        protected List<EntidadeEmpresa> doInBackground(Void... strings) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return new EmpresaDAO().getListaEmpresa(pesq);
        }

        @Override
        protected void onPreExecute() {
            this.mProgress = ProgressDialog.show(MenuPrincipalEmpresa.this, null, "Processando...", true);
            this.mProgress.setIndeterminate(false);
            this.mProgress.setCancelable(false);
        }

        @Override
        protected void onPostExecute(List<EntidadeEmpresa> result) {
            try {
                // Carrega todos os pontos
                setCarregarEmpresas(result);
            } catch (Exception e) {
                e.printStackTrace();
            }

            mProgress.dismiss();
            super.onPostExecute(result);
        }
    }

    /**
     * Exibe o "modal" para mostrar uma mensagem de aviso qualquer
     *
     * @author Igor Maximo <igormaximo_1989@hotmail.com>
     * @data 18/04/2019
     */
    public void setGeraPopUpAlertaMsg(String msg, Context context, int layoutToastMsg, boolean seLongoTempo) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View layout = inflater.inflate(layoutToastMsg, (ViewGroup) ((Activity) context).findViewById(R.id.toast_layout_root_autorizada));
        TextView text = (TextView) layout.findViewById(R.id.textMsg);
        text.setText(msg);
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        if (seLongoTempo) {
            toast.setDuration(Toast.LENGTH_LONG);
        } else {
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        toast.setView(layout);
        toast.show();
    }

    /**
     * AsyncTask para o cadastrar
     * concluir o cadastro do cliente
     *
     * @author Igor Maximo <igormaximo_1989@hotmail.com>
     * @data 20/06/2020
     */
    @SuppressLint("StaticFieldLeak")
    private class AsyncTaskSetCompletarCadastro extends AsyncTask<EntidadeCadastroUsuario, Integer, Object[]> {
        private ProgressDialog mProgress = null;

        @Override
        protected void onProgressUpdate(Integer... progress) {
            mProgress.setProgress(progress[0]);
        }

        @Override
        protected Object[] doInBackground(EntidadeCadastroUsuario... entidadeCadastroUsuario) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // DAO
            return new UsuarioDAO().setCompletarCadastroUsuario(entidadeCadastroUsuario[0]);
        }

        @Override
        protected void onPreExecute() {
            this.mProgress = ProgressDialog.show(MenuPrincipalEmpresa.this, null, "Processando...", true);
            this.mProgress.setIndeterminate(false);
            this.mProgress.setCancelable(false);
        }

        @Override
        protected void onPostExecute(Object[] result) {
            mProgress.dismiss();
            if (Boolean.parseBoolean(result[0] + "")) {
                setGeraPopUpAlertaMsg(result[1] + "", MenuPrincipalEmpresa.this, R.layout.toast_msg_sucesso, false);
                pesq = "";
                // Carrega empresas
                new AsyncTaskGetEmpresa().execute();
                // Habilita layoout
                setExpandeColapsaBottomSheet(BottomSheetBehavior.STATE_EXPANDED, 0);
                View bottomSheet = findViewById(R.id.bottom_sheet_completar_cadastro_endereco);
                final BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
                final SearchView searchViewPesquisaEmpresa = (SearchView) findViewById(R.id.searchViewPesquisaEmpresa);
                LinearLayout buttonBotaoPesquisarEmpresa = (LinearLayout) findViewById(R.id.buttonBotaoPesquisarEmpresa);
                // Campo botão campo pesquisa search view
                buttonBotaoPesquisarEmpresa.setFocusable(true);
                buttonBotaoPesquisarEmpresa.setEnabled(true);
                buttonBotaoPesquisarEmpresa.setClickable(true);
                buttonBotaoPesquisarEmpresa.setVisibility(View.VISIBLE);
                // Disabilita Search View
                searchViewPesquisaEmpresa.setFocusable(true);
                searchViewPesquisaEmpresa.setEnabled(true);
                searchViewPesquisaEmpresa.setClickable(true);
                searchViewPesquisaEmpresa.setVisibility(View.VISIBLE);
                // Desabilita recyclerview empresas
                RecyclerView recyclerViewsPontos = (RecyclerView) findViewById(R.id.recyclerViewsPontos);
                recyclerViewsPontos.setFocusable(true);
                recyclerViewsPontos.setEnabled(true);
                recyclerViewsPontos.setClickable(true);
                recyclerViewsPontos.setVisibility(View.VISIBLE);
                // Se completou cadastro
                seCompletouCadastro = true;
                // Esconde bottomshet completar cadastro
                setExpandeColapsaBottomSheet(BottomSheetBehavior.STATE_COLLAPSED, 0);
            } else {
                setGeraPopUpAlertaMsg(result[1] + "", MenuPrincipalEmpresa.this, R.layout.toast_msg_erro, true);
            }
            super.onPostExecute(result);
        }
    }


    private LocationManager mgr;
    private String best;
    public static double myLocationLatitude;
    public static double myLocationLongitude;

    private void dumpLocation(Location l) {

        if (l == null) {

            myLocationLatitude = 0.0;
            myLocationLongitude = 0.0;
        } else {

            myLocationLatitude = l.getLatitude();
            myLocationLongitude = l.getLongitude();
        }
    }

    /**
     * Ponte de escuta para as consultas de geolocalização
     *
     * @author Igor Maximo <igormaximo_1989@hotmail.com>
     * @date 17/10/2019
     * @updated 17/10/2019
     */
    public Object[] solicitaGeoLocalizacao() {
        if (Build.VERSION.SDK_INT >= 23) {
            String[] PERMISSIONS = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
            ActivityCompat.requestPermissions((Activity) MenuPrincipalEmpresa.this, PERMISSIONS, 2);
        }
        mgr = (LocationManager) getSystemService(LOCATION_SERVICE);
        dumpProviders();
        Criteria criteria = new Criteria();
        best = mgr.getBestProvider(criteria, true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return null;
            }
        }
        Location location = mgr.getLastKnownLocation(best);
        dumpLocation(location);

        System.err.println("GPS => " + location.getLatitude() + ", " + location.getLongitude());

        return new Object[]{location.getLatitude(), location.getLongitude()};
    }


    @Override
    protected void onPause() {
        super.onPause();
        mgr.removeUpdates(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onResume() {

        super.onResume();
        try {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    Activity#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                return;
            }

            mgr.requestLocationUpdates(best, 15000, 1, this);
        } catch (Exception e) {
            System.err.println(e);
        }
    }


    private void dumpProviders() {

        List<String> providers = mgr.getAllProviders();
        for (String p : providers) {

            dumpProviders(p);
        }
    }

    private void dumpProviders(String s) {

        LocationProvider info = mgr.getProvider(s);
        StringBuilder builder = new StringBuilder();
        builder.append("name: ").append(info.getName());
    }


    @Override
    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
//                || super.onSupportNavigateUp();
        return true;
    }


}

package com.kylukz.fooddev.View;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.aide.aiDelivery.R;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.kylukz.fooddev.Animation.Animatoo;
import com.kylukz.fooddev.Animation.MyBounceInterpolator;
import com.kylukz.fooddev.Animation.Sombreamento;
import com.kylukz.fooddev.DAO.LogDAO;
import com.kylukz.fooddev.DAO.UsuarioDAO;
import com.kylukz.fooddev.JavaBeans.EntidadeCadastroUsuario;
import com.kylukz.fooddev.JavaBeans.EntidadeUsuario;
import com.kylukz.fooddev.SQLite.GeraTabelasSQLite;
import com.kylukz.fooddev.SQLite.SQLiteGeraTabelaGerenciamento;
import com.kylukz.fooddev.Toolbox.Connectivity;
import com.kylukz.fooddev.Toolbox.Ferramentas;
import com.kylukz.fooddev.Toolbox.Mask;
import com.kylukz.fooddev.Valid.ValidaCPF;
import com.kylukz.fooddev.Valid.ValidadorCadastro;
import com.kylukz.fooddev.Valid.ValidadorLogin;

import java.util.Calendar;

/**
 * Classe de login/auth de usuários
 *
 * @author Igor Maximo <igormaximo_1989@hotmail.com>
 */

public class MainActivity extends AppCompatActivity {

    private GoogleSignInClient mGoogleSignInClient;
    protected SQLiteGeraTabelaGerenciamento sqLiteGeraTabelaGerenciamento = new SQLiteGeraTabelaGerenciamento(MainActivity.this);

    private EntidadeUsuario entidadeUsuario = new EntidadeUsuario();
    EditText editTextCampoLogin;
    EditText editTextCampoSenha;

    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;
    Calendar calendar = Calendar.getInstance();
    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    private TextView mStatusTextView;
    private TextView mDetailTextView;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide(); // remove barra de título
        getWindow().getDecorView();
        setContentView(R.layout.activity_main);

        // Permissão gps
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        // Componentes declaração padrão
        ImageView imageView = (ImageView) findViewById(R.id.imageViewLogoMarca);
        editTextCampoLogin = (EditText) findViewById(R.id.editTextCampoLogin);
        editTextCampoSenha = (EditText) findViewById(R.id.editTextCampoSenha);
        Button buttonBotaoAcessar = (Button) findViewById(R.id.buttonBotaoAcessar);
        LinearLayout buttonBotaoAcessarGoogle = (LinearLayout) findViewById(R.id.buttonBotaoAcessarGoogle);
        LinearLayout buttonBotaoAcessarFacebook = (LinearLayout) findViewById(R.id.buttonBotaoAcessarFacebook);
        TextView textViewCreditos = (TextView) findViewById(R.id.textViewCreditos);

        //////////////////////////// SQLITE GERA DB //////////////////////////////
        GeraTabelasSQLite dbh = new GeraTabelasSQLite(MainActivity.this);
        dbh.getWritableDatabase();
        //////////////////////////////////////////////////////////////////////////

        try {
            // SQLite
            String[] infos = sqLiteGeraTabelaGerenciamento.getSelectUltimoLogin();
            if (infos[1].length() == 8 && infos[2].length() >= 5) {
                // Preenche campos com credenciais salvas
                editTextCampoLogin.setText(infos[1]);
                editTextCampoSenha.setText(infos[2]);
                try {
                    // Chama código do botão de Acessar (amarelo simples)
                    setAutenticarPorPin();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        // Desabilitando recursos informativos
        textViewCreditos.setFocusable(false);
        textViewCreditos.setEnabled(false);

        editTextCampoLogin.getBackground().setColorFilter(R.color.colorPreta, PorterDuff.Mode.SRC_IN);
        editTextCampoSenha.getBackground().setColorFilter(R.color.colorPreta, PorterDuff.Mode.SRC_IN);

        // Aplicação de efeitos de sombreamento nos componentes da tela
        Sombreamento.setSombraComponente(150.0f, 1000, 1000, buttonBotaoAcessar, R.color.colorPrimaryDark);
        //Cores
        editTextCampoLogin.getBackground().setColorFilter(R.color.colorPreta, PorterDuff.Mode.SRC_IN);
        editTextCampoSenha.getBackground().setColorFilter(R.color.colorPreta, PorterDuff.Mode.SRC_IN);

        // Auth simples
        buttonBotaoAcessar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAutenticarPorPin();
            }
        });

        // Auth por Google (Gmail)
        buttonBotaoAcessarGoogle.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {


//                LayoutInflater inflater = ((Activity) MainActivity.this).getLayoutInflater();
//                View layout = inflater.inflate(R.layout.toast_msg_alerta, null);
//                TextView text = (TextView) layout.findViewById(R.id.textMsg);
//                text.setText("Em construção!");
//                Toast toast = new Toast(MainActivity.this);
//                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
//                toast.setDuration(Toast.LENGTH_SHORT);
//                toast.setView(layout);
//                toast.show();


                // Configure Google Sign In
//                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                        .requestIdToken(getString(R.string.default_web_client_id))
//                        .requestEmail()
//                        .build();
//                mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
            }
        });

        // Auth por Facebook
        buttonBotaoAcessarFacebook.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
//                LayoutInflater inflater = ((Activity) MainActivity.this).getLayoutInflater();
//                View layout = inflater.inflate(R.layout.toast_msg_alerta, null);
//                TextView text = (TextView) layout.findViewById(R.id.textMsg);
//                text.setText("Em construção!");
//                Toast toast = new Toast(MainActivity.this);
//                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
//                toast.setDuration(Toast.LENGTH_SHORT);
//                toast.setView(layout);
//                toast.show();
            }
        });


//////////////////////////// EFEITO NUBANK INTERPOLAÇÃO ///////////////////////////////////////////////////////
        try {
            Animatoo.animateSwipeRight(this);
            final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.enter_from_right);
            // Use bounce interpolator with amplitude 0.1 and frequency 15
            MyBounceInterpolator interpolator = new MyBounceInterpolator(0.1, 25);
            myAnim.setInterpolator(interpolator);
            editTextCampoLogin.startAnimation(myAnim);
        } catch (Exception e) {
            System.err.println(e);
        }
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        // Cadastrar-se
        TextView textViewCadastrarse = (TextView) findViewById(R.id.textViewCadastrarse);
        textViewCadastrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Limpa
                setLimpaCampos(0);
                setExibeColapsaCompletarCadastro(0);
            }
        });

        // Esqueci a senha
        TextView textViewEsqueciSenha = (TextView) findViewById(R.id.textViewEsqueciSenha);
        textViewEsqueciSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Limpa
                setLimpaCampos(1);
                setExibeColapsaCompletarCadastro(1);
            }
        });


        final EditText editTextCampoNome = (EditText) findViewById(R.id.editTextCampoNome);
        final EditText editTextCampoSobrenome = (EditText) findViewById(R.id.editTextCampoSobrenome);
        final EditText editTextCampoCPF = (EditText) findViewById(R.id.editTextCampoCPF);
        final EditText editTextCampoNascimento = (EditText) findViewById(R.id.editTextCampoNascimento);
        final EditText editTextCampoCelular = (EditText) findViewById(R.id.editTextCampoCelular);
        final EditText editTextCampoEmail = (EditText) findViewById(R.id.editTextCampoEmail);
        final EditText editTextCampoSenhaCadastro = (EditText) findViewById(R.id.editTextCampoSenhaCadastro);
        editTextCampoCelular.addTextChangedListener(Mask.insert("(##) #####-####", editTextCampoCelular));
        // Botão cadastrar pin
        Button buttonBotaoSolicitarPIN = (Button) findViewById(R.id.buttonBotaoSolicitarPIN);
        // Botão esqueci pin e mandar por e-mail
        Button buttonBotaoSolicitarPINEsqueciSenhaEmail = (Button) findViewById(R.id.buttonBotaoSolicitarPINEsqueciSenhaEmail);
        final EditText editTextCampoCPFEsqueciSenha = (EditText) findViewById(R.id.editTextCampoCPFEsqueciSenha);

        // Máscaras de CPF
        editTextCampoCPFEsqueciSenha.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (editTextCampoCPFEsqueciSenha.getText().toString().length() == 11) {
                        editTextCampoCPFEsqueciSenha.setText(Ferramentas.setMascaraCPF(editTextCampoCPFEsqueciSenha.getText().toString()));
                    }
                }
            }
        });
        editTextCampoCPFEsqueciSenha.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode != KeyEvent.KEYCODE_DEL) {
                    if (editTextCampoCPFEsqueciSenha.getText().toString().length() == 11) {
                        editTextCampoCPFEsqueciSenha.setText(Ferramentas.setMascaraCPF(editTextCampoCPFEsqueciSenha.getText().toString()));
                    }
                }
                return false;
            }
        });

        buttonBotaoSolicitarPINEsqueciSenhaEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (editTextCampoCPFEsqueciSenha.getText().length() == 14) {
                        if (ValidaCPF.valida(editTextCampoCPFEsqueciSenha.getText().toString().replace("-", "").replace(".", ""))) {
                            new AsyncTaskEsqueciSenhaUsuario().execute(editTextCampoCPFEsqueciSenha.getText().toString());
                        } else {
                            editTextCampoCPFEsqueciSenha.setError("CPF inválido!");
                        }
                    } else {
                        editTextCampoCPFEsqueciSenha.setError("CPF inválido!");
                    }
                } catch (Exception e) {

                }
            }
        });
        // Entidade
        final EntidadeCadastroUsuario entidadeCadastroUsario = new EntidadeCadastroUsuario();
        // Máscaras de CPF
        editTextCampoCPF.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (editTextCampoCPF.getText().toString().length() == 11) {
                        editTextCampoCPF.setText(Ferramentas.setMascaraCPF(editTextCampoCPF.getText().toString()));
                    }
                }
            }
        });
        editTextCampoCPF.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode != KeyEvent.KEYCODE_DEL) {
                    if (editTextCampoCPF.getText().toString().length() == 11) {
                        editTextCampoCPF.setText(Ferramentas.setMascaraCPF(editTextCampoCPF.getText().toString()));
                    }
                }
                return false;
            }
        });
        // Pega data de nascimento
        final DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                // Máscara de data
                Ferramentas.setMascaraDataPicker(calendar, MainActivity.this, R.id.editTextCampoNascimento);
            }
        };
        //Campo Data nascimento datapicker
        editTextCampoNascimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(MainActivity.this, date1, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        // Set formulário
        buttonBotaoSolicitarPIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // Validação
                    ValidadorCadastro validaFormularioCadastroPinPorCPF = new ValidadorCadastro();
                    // Carrega entidade
                    entidadeCadastroUsario.setNome(editTextCampoNome.getText().toString());
                    entidadeCadastroUsario.setSobreNome(editTextCampoSobrenome.getText().toString());
                    entidadeCadastroUsario.setCpf(editTextCampoCPF.getText().toString());
                    entidadeCadastroUsario.setDataNascimento(editTextCampoNascimento.getText().toString());
                    entidadeCadastroUsario.setCelular(editTextCampoCelular.getText().toString());
                    entidadeCadastroUsario.setEmail(editTextCampoEmail.getText().toString());
                    entidadeCadastroUsario.setSenha(editTextCampoSenhaCadastro.getText().toString());
                    // Set validar
                    Object[] valida = new Object[3];
                    valida = validaFormularioCadastroPinPorCPF.setValidaFormularioCadastroPinPorCPF(entidadeCadastroUsario);
                    // Nome
                    if (!Boolean.parseBoolean(String.valueOf(valida[0])) && Integer.parseInt(String.valueOf(valida[2])) == 0) {
                        System.out.println("====================> 1");
                        editTextCampoNome.setError(valida[1] + "");
                        return;
                    }
                    // Sobrenome
                    if (!Boolean.parseBoolean(String.valueOf(valida[0])) && Integer.parseInt(String.valueOf(valida[2])) == 1) {
                        System.out.println("====================> 2");
                        editTextCampoSobrenome.setError(valida[1] + "");
                        return;
                    }
                    // CPF
                    if (!Boolean.parseBoolean(String.valueOf(valida[0])) && Integer.parseInt(String.valueOf(valida[2])) == 2) {
                        System.out.println("====================> 3");
                        editTextCampoCPF.setError(valida[1] + "");
                        return;
                    }
                    // Nascimento
                    if (!Boolean.parseBoolean(String.valueOf(valida[0])) && Integer.parseInt(String.valueOf(valida[2])) == 3) {
                        System.out.println("====================> 4");
                        editTextCampoNascimento.setError(valida[1] + "");
                        return;
                    }
                    // Celular
                    if (!Boolean.parseBoolean(String.valueOf(valida[0])) && Integer.parseInt(String.valueOf(valida[2])) == 4) {
                        System.out.println("====================> 5");
                        editTextCampoCelular.setError(valida[1] + "");
                        return;
                    }
                    // E-mail
                    if (!Boolean.parseBoolean(String.valueOf(valida[0])) && Integer.parseInt(String.valueOf(valida[2])) == 5) {
                        System.out.println("====================> 6");
                        editTextCampoEmail.setError(valida[1] + "");
                        return;
                    }
                    // Senha
                    if (!Boolean.parseBoolean(String.valueOf(valida[0])) && Integer.parseInt(String.valueOf(valida[2])) == 6) {
                        System.out.println("====================> 7");
                        editTextCampoSenhaCadastro.setError(valida[1] + "");
                        return;
                    }
                    // DAO
                    new AsyncTaskSetCadastrarUsuario().execute(entidadeCadastroUsario);
                } catch (Exception e) {

                }
            }
        });
    }

    /**
     * Limpa campos de cadastro
     *
     * @author Igor Maximo <igormaximo_1989@hotmail.com>
     * @date 18/06/2020
     */
    private void setLimpaCampos(int layoutBottomSheet) {
        switch (layoutBottomSheet) {
            case 0:
                // Novo Cadastro
                final EditText editTextCampoNome = (EditText) findViewById(R.id.editTextCampoNome);
                final EditText editTextCampoSobrenome = (EditText) findViewById(R.id.editTextCampoSobrenome);
                final EditText editTextCampoCPF = (EditText) findViewById(R.id.editTextCampoCPF);
                final EditText editTextCampoNascimento = (EditText) findViewById(R.id.editTextCampoNascimento);
                final EditText editTextCampoCelular = (EditText) findViewById(R.id.editTextCampoCelular);
                final EditText editTextCampoEmail = (EditText) findViewById(R.id.editTextCampoEmail);
                final EditText editTextCampoSenhaCadastro = (EditText) findViewById(R.id.editTextCampoSenhaCadastro);
                editTextCampoNome.setText("");
                editTextCampoSobrenome.setText("");
                editTextCampoCPF.setText("");
                editTextCampoNascimento.setText("");
                editTextCampoCelular.setText("");
                editTextCampoEmail.setText("");
                editTextCampoSenhaCadastro.setText("");
                break;
            case 1:
                // Esqueci senha
                final EditText editTextCampoCPFEsqueciSenha = (EditText) findViewById(R.id.editTextCampoCPFEsqueciSenha);
                editTextCampoCPFEsqueciSenha.setText("");
                break;
        }


    }

    // [START auth_with_google]
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        // [START_EXCLUDE silent]
//        showProgressDialog();
        // [END_EXCLUDE]

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
//                            Snackbar.make(findViewById(R.id.main_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // [START_EXCLUDE]
//                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
    }

    // [START signin]
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    // [END signin]

    private void signOut() {
        // Firebase sign out
        mAuth.signOut();

        // Google sign out
        mGoogleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        updateUI(null);
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
//        hideProgressDialog();
//        if (user != null) {
//            mStatusTextView.setText(getString(R.string.google_status_fmt, user.getEmail()));
//            mDetailTextView.setText(getString(R.string.firebase_status_fmt, user.getUid()));
//
//            findViewById(R.id.signInButton).setVisibility(View.GONE);
//            findViewById(R.id.signOutAndDisconnect).setVisibility(View.VISIBLE);
//        } else {
//            mStatusTextView.setText(R.string.signed_out);
//            mDetailTextView.setText(null);
//
//            findViewById(R.id.signInButton).setVisibility(View.VISIBLE);
//            findViewById(R.id.signOutAndDisconnect).setVisibility(View.GONE);
//        }
    }

    private void revokeAccess() {
        // Firebase sign out
        mAuth.signOut();

        // Google revoke access
        mGoogleSignInClient.revokeAccess().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        updateUI(null);
                    }
                });
    }

    private BottomSheetBehavior mBottomSheetBehavior;

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
                bottomSheet = findViewById(R.id.bottom_sheet_cadastra_pin);
                break;
            case 1:
                bottomSheet = findViewById(R.id.bottom_sheet_esqueci_senha);
                break;
        }
        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        mBottomSheetBehavior.setState(state);
        bottomSheet.requestLayout();
        bottomSheet.invalidate();
    }

    /**
     * Exibe a roleta de produtos
     */
    public void setExibeColapsaCompletarCadastro(int bottomSheetAcao) {
        try {
            View bottomSheet = null;
            switch (bottomSheetAcao) {
                case 0:
                    bottomSheet = findViewById(R.id.bottom_sheet_cadastra_pin);
                    break;
                case 1:
                    bottomSheet = findViewById(R.id.bottom_sheet_esqueci_senha);
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
     * Método do botão "Acessar" simples
     *
     * @author Igor Maximo <igormaximo_1989@hotmail.com>
     * @date 26/07/2020
     */
    private void setAutenticarPorPin() {
        try {
            ValidadorLogin validadorLogin = new ValidadorLogin();
            // Set validar
            Object[] valida = new Object[3];
            valida = validadorLogin.setValidaFormularioLoginPorPin(editTextCampoLogin.getText().toString(), editTextCampoSenha.getText().toString());
            // Validação
            if (!Boolean.parseBoolean(String.valueOf(valida[0])) && Integer.parseInt(String.valueOf(valida[2])) == 0) {
                editTextCampoLogin.setError(valida[1] + "");
                return;
            }
            if (!Boolean.parseBoolean(String.valueOf(valida[0])) && Integer.parseInt(String.valueOf(valida[2])) == 1) {
                editTextCampoSenha.setError(valida[1] + "");
                return;
            }
            // Auth
            if (Connectivity.isConnected(MainActivity.this)) {
                new AsyncTaskGetUsuario().execute(editTextCampoLogin.getText().toString(), editTextCampoSenha.getText().toString()); // Realiza consulta no servidor quando for autenticação por PIN
            } else {
                setGeraPopUpAlertaMsg("Não há conexão!", MainActivity.this, R.layout.toast_msg_alerta, true);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            LogDAO.setErro("MainActivity", "buttonBotaoAcessar", e.getMessage(), 81, 0, new EntidadeUsuario().getId(), new EntidadeUsuario().getFkVersionamento());
        }
    }

    /**
     * AsyncTask para o carregamento dos dados
     * do usuário
     *
     * @author Igor Maximo <igormaximo_1989@hotmail.com>
     * @data 09/05/2020
     */
    @SuppressLint("StaticFieldLeak")
    private class AsyncTaskGetUsuario extends AsyncTask<String, Integer, EntidadeUsuario> {
        private ProgressDialog mProgress = null;

        @Override
        protected void onProgressUpdate(Integer... progress) {
            mProgress.setProgress(progress[0]);
        }

        @Override
        protected EntidadeUsuario doInBackground(String... strings) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return new UsuarioDAO().getUsuario(strings[0], strings[1]);
        }

        @Override
        protected void onPreExecute() {
            this.mProgress = ProgressDialog.show(MainActivity.this, null, "Processando...", true);
            this.mProgress.setIndeterminate(false);
            this.mProgress.setCancelable(false);
        }

        @Override
        protected void onPostExecute(EntidadeUsuario result) {
            UsuarioDAO.ENTIDADE_USUARIO = result;
            setLogar(result);
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
     * o PIN de um novo usuário
     *
     * @author Igor Maximo <igormaximo_1989@hotmail.com>
     * @data 17/06/2020
     */
    @SuppressLint("StaticFieldLeak")
    private class AsyncTaskSetCadastrarUsuario extends AsyncTask<EntidadeCadastroUsuario, Integer, Object[]> {
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
            return new UsuarioDAO().setCadastrarUsuario(entidadeCadastroUsuario[0]);
        }

        @Override
        protected void onPreExecute() {
            try {
                this.mProgress = ProgressDialog.show(MainActivity.this, null, "Processando...", true);
                this.mProgress.setIndeterminate(false);
                this.mProgress.setCancelable(false);
            } catch (Exception ignored) {
                // do nothing, activity keeps working
            }
        }

        @Override
        protected void onPostExecute(Object[] result) {
            mProgress.dismiss();
            if (Boolean.parseBoolean(result[0] + "")) {
                setGeraPopUpAlertaMsg(result[1] + "", MainActivity.this, R.layout.toast_msg_sucesso, false);
                // Esconde bottomsheet
                setExpandeColapsaBottomSheet(BottomSheetBehavior.STATE_COLLAPSED, 0);
                // Limpa
                setLimpaCampos(0);
            } else {
                setGeraPopUpAlertaMsg(result[1] + "", MainActivity.this, R.layout.toast_msg_erro, true);
            }
            super.onPostExecute(result);
        }
    }

    /**
     * AsyncTask para o cadastrar
     * o PIN de um novo usuário
     *
     * @author Igor Maximo <igormaximo_1989@hotmail.com>
     * @data 17/06/2020
     */
    @SuppressLint("StaticFieldLeak")
    private class AsyncTaskEsqueciSenhaUsuario extends AsyncTask<String, Integer, Object[]> {
        private ProgressDialog mProgress = null;

        @Override
        protected void onProgressUpdate(Integer... progress) {
            mProgress.setProgress(progress[0]);
        }

        @Override
        protected Object[] doInBackground(String... cpf) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // DAO
            return new UsuarioDAO().setEnviarPINUsuarioPorEmail(cpf[0]);
        }

        @Override
        protected void onPreExecute() {
            this.mProgress = ProgressDialog.show(MainActivity.this, null, "Processando...", true);
            this.mProgress.setIndeterminate(false);
            this.mProgress.setCancelable(false);
        }

        @Override
        protected void onPostExecute(Object[] result) {
            mProgress.dismiss();
            if (Boolean.parseBoolean(result[0] + "")) {
                setGeraPopUpAlertaMsg(result[1] + "", MainActivity.this, R.layout.toast_msg_sucesso, false);
                // Esconde bottomsheet
                setExpandeColapsaBottomSheet(BottomSheetBehavior.STATE_COLLAPSED, 1);
                // Limpa
                setLimpaCampos(1);
            } else {
                setGeraPopUpAlertaMsg(result[1] + "", MainActivity.this, R.layout.toast_msg_erro, true);
            }
            super.onPostExecute(result);
        }
    }


    /**
     * Método que autentica o usuário
     * e aramzena os dados para uso posterior
     * em toda a aplicação
     *
     * @param entidadeUsuario
     * @author Igor Maximo <igormaximo_1989@hotmail.com>
     * @date 12/05/2020
     */
    public void setLogar(EntidadeUsuario entidadeUsuario) {
        try {
            // Se autenticação por PIN
            if (entidadeUsuario.isSeAuth()) {
                if (entidadeUsuario.getPin().equals(editTextCampoLogin.getText().toString())
                        && entidadeUsuario.getSenha().equals(editTextCampoSenha.getText().toString()) && entidadeUsuario.getPin() != null && entidadeUsuario.getSenha() != null) {
                    ///////// SQLITE /////////
                    sqLiteGeraTabelaGerenciamento.setAtualizaLogin(entidadeUsuario.getPin(), entidadeUsuario.getSenha(), "PIN");
                    Intent intenetAuthPrincipal = new Intent(MainActivity.this, MenuPrincipalEmpresa.class);
                    startActivity(intenetAuthPrincipal);
                    finish();
                } else {
                    setGeraPopUpAlertaMsg(entidadeUsuario.getMsgAuth() + "", MainActivity.this, R.layout.toast_msg_erro, true);
                }
            } else {
                setGeraPopUpAlertaMsg(entidadeUsuario.getMsgAuth() + "", MainActivity.this, R.layout.toast_msg_erro, true);
            }
        } catch (Exception e) {
//            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}



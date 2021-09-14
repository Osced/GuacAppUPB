package com.upb.laguaca;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class MainActivityPregunta extends AppCompatActivity {

    private TextView pregunta;
    private TextView temporizador;
    private TextView textViewPuntaje;
    private EditText respuesta1113;//input tipo texto para guardar respuesta de la pregunta 11 o13
    private EditText respuesta14;//input tipo texto para respuesta final 14
    private Button botonEnviarRespuesta;
    private Button botonVisitarOtroPunto;
    private Button botonLeerQr;
    private Bundle participanteGuaca;
    private RadioGroup radioGroupRespuestasGuaca;


    private ProgressDialog dialogoRegistrarRespuesta;
    private String ipServidor;
    private String url;
    private StringRequest stringRequest;
    private RequestQueue request;

    private int idPregunta;
    private int numeroPreguntasrespondidas;
    private int periodoInicio;
    private int puntaje=0;
    private int puntos=0;
    int h = 0;
    int m = 0;
    int s = 0;
    private String respuestaSeleccionada = "";
    private String idUPB;
    private String nombreParticipante;
    private String preguntasRespondidas[];
    private SharedPreferences preferenciasParticipante;
    private Set<String> preguntasParticipante;

    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private RadioButton rb4;
    private long milisegundos;

    private Window ventana;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_pregunta);
        pregunta = findViewById(R.id.textViewResponderPregunta);
        temporizador = findViewById(R.id.textViewTemporizador);
        textViewPuntaje=findViewById(R.id.textViewPuntaje);
        respuesta1113 = findViewById(R.id.editTextRespuesta1113);
        respuesta14 = findViewById(R.id.editTextMultiLineR14);
        botonEnviarRespuesta = findViewById(R.id.buttonResponderEnviarPregunta);
        botonEnviarRespuesta.setOnClickListener(clicBotonEnviarRespuesta);

        botonVisitarOtroPunto = findViewById(R.id.buttonVisitarOtroPunto);
        botonLeerQr = findViewById(R.id.buttonQr);
        botonLeerQr.setOnClickListener(clicBotonLeerQr);
        botonVisitarOtroPunto.setOnClickListener(clicBotonVisitarOtroPunto);

        rb1 = findViewById(R.id.radioButtonR1);
        rb2 = findViewById(R.id.radioButtonR2);
        rb3 = findViewById(R.id.radioButtonR3);
        rb4 = findViewById(R.id.radioButtonR4);
        radioGroupRespuestasGuaca = findViewById(R.id.radioGroupRespuestasGuaca);


        getSupportActionBar().hide();
        ventana = getWindow();
        ventana.setStatusBarColor(Color.parseColor("#38020B"));

        /*participanteGuaca=this.getIntent().getExtras();

        if(participanteGuaca!=null)
        {
            idPregunta=participanteGuaca.getInt("puntoActual");
            idUPB=participanteGuaca.getString("idUPB");
            nombreParticipante=participanteGuaca.getString("nombreParticipante");
            cargarPregunta(participanteGuaca.getInt("puntoActual"));
        }*/
        preferenciasParticipante = getSharedPreferences("InfoParticipante", Context.MODE_PRIVATE);
        cargarPreferenciasParticipante();
        cargarPregunta(idPregunta);
        iniciarTemporizador();

        /*long milisegundos=(h*3600000)+(m*60000)+(s*1000);
        System.out.println("Desde antyes77777777777777777777777777777777777777777777   "+milisegundos);
        Tiempo regresivo= new Tiempo(milisegundos,1000);
        regresivo.start();*/


        botonEnviarRespuesta.setOnClickListener(clicBotonEnviarRespuesta);
        rb1.setOnClickListener(clicRb1);
        rb2.setOnClickListener(clicRb2);
        rb3.setOnClickListener(clicRb3);
        rb4.setOnClickListener(clicRb4);



    }

    public class Tiempo extends CountDownTimer {

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public Tiempo(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            int hours= (int) ((millisUntilFinished/1000)/3600);
            int minutes= (int) ((millisUntilFinished/1000)%3600)/60;
            int seconds= (int) ((millisUntilFinished/1000)%60);

            String timeLeftFormated;
            if (hours>0){
               timeLeftFormated=String.format(Locale.getDefault(),"%d:%02d:%02d", hours, minutes,seconds);
            }else{
                timeLeftFormated=String.format(Locale.getDefault(),"%02d:%02d", minutes,seconds);
            }

            temporizador.setText("Quedan: "+ timeLeftFormated);
            textViewPuntaje.setText("Tienes: "+ puntaje+" ptos.");
        }

        @Override
        public void onFinish() {

            //temporizador.setText("Termino GAMEOVER");
            Toast.makeText(MainActivityPregunta.this, "GAME OVER-TIEMPO FINALIZADO", Toast.LENGTH_LONG).show();
            Intent irPantalla=new Intent(MainActivityPregunta.this,MainActivity.class);
            startActivity(irPantalla);
            //finish();
        }
    }





    private void iniciarTemporizador() {
        GregorianCalendar f=new GregorianCalendar();
        int hp = periodoInicio+22;//carga la hora del periodo de cada Participante
        int mp = 30;
        int sp = 0;
        int ha = f.get(Calendar.HOUR_OF_DAY);;//carga la hora actual
        int ma = f.get(Calendar.MINUTE);
        int sa = f.get(Calendar.SECOND);

            if(sp>=sa)

        {
            s = sp - sa;
        }
            else

        {
            if (mp >= ma) {
                sp = sp + 60;
                mp = mp - 1;
                s = sp - sa;
            } else {
                mp = mp + 60;
                mp = mp - 1;
                hp = hp - 1;
                sp = sp + 60;
                s = sp - sa;
            }
        }

            if(mp>=ma)

        {
            m = mp - ma;
        }
            else

        {
            mp = mp + 60;
            m = mp - ma;
            hp = hp - 1;
        }

        h =hp -ha;

            temporizador.setText(h+":"+m+":"+s +"---------"+ha+":"+ma+":"+sa);

        milisegundos=(h*3600000)+(m*60000)+(s*1000);
        System.out.println("Desde antyes77777777777777777777777777777777777777777777   "+milisegundos);
        Tiempo regresivo= new Tiempo(milisegundos,1000);
        regresivo.start();
    }



    private void cargarPreferenciasParticipante() {

        nombreParticipante=preferenciasParticipante.getString("nombreParticipante","noHayUsuario");
        idUPB=preferenciasParticipante.getString("idUPB","noHayUsuario");
        numeroPreguntasrespondidas=preferenciasParticipante.getInt("puntoActual",0);
        puntaje=preferenciasParticipante.getInt("puntaje",0);
        idPregunta=preferenciasParticipante.getInt("numeroPregunta",0);
        periodoInicio= preferenciasParticipante.getInt("periodoInicio",0);
        Toast.makeText(MainActivityPregunta.this, " Número Pregunta: "+idPregunta, Toast.LENGTH_LONG).show();


    }

    private void cargarPregunta(int numeroPreguntaGuaga) {

        String titulo="";
        String mensaje="";

        respuesta1113.setVisibility(View.GONE);
        respuesta14.setVisibility(View.GONE);
        botonEnviarRespuesta.setText("ENVIAR RESPUESTA");
        botonLeerQr.setVisibility(View.GONE);
        switch(numeroPreguntaGuaga){
            case 1:

                pregunta.setText(R.string.pregunta1);
                rb1.setText(R.string.p1_op1);
                rb2.setText(R.string.p1_op2);
                rb3.setText(R.string.p1_op3);
                rb4.setText(R.string.p1_op4);
                if(preferenciasParticipante.getInt("p1",0)!=0){
                    Toast.makeText(MainActivityPregunta.this, "Pregunta número 1 ya fué registrada" , Toast.LENGTH_LONG).show();
                    rb1.setVisibility(View.GONE);
                    rb2.setVisibility(View.GONE);
                    rb3.setVisibility(View.GONE);
                    rb4.setVisibility(View.GONE);
                    botonEnviarRespuesta.setVisibility(View.GONE);
                    botonVisitarOtroPunto.setVisibility(View.VISIBLE);
                    radioGroupRespuestasGuaca.setVisibility(View.GONE);
                }

                break;
            case 2:
                pregunta.setText(R.string.pregunta2);
                rb1.setText(R.string.p2_op1);
                rb2.setText(R.string.p2_op2);
                rb3.setText(R.string.p2_op3);
                rb4.setText(R.string.p2_op4);
                if(preferenciasParticipante.getInt("p2",0)!=0){
                    Toast.makeText(MainActivityPregunta.this, "Pregunta número 2 ya fué registrada" , Toast.LENGTH_LONG).show();
                    rb1.setVisibility(View.GONE);
                    rb2.setVisibility(View.GONE);
                    rb3.setVisibility(View.GONE);
                    rb4.setVisibility(View.GONE);
                    botonEnviarRespuesta.setVisibility(View.GONE);
                    botonVisitarOtroPunto.setVisibility(View.VISIBLE);
                }
                break;
            case 3:
                pregunta.setText(R.string.pregunta3);
                rb1.setText(R.string.p3_op1);
                rb2.setText(R.string.p3_op2);
                rb3.setText(R.string.p3_op3);
                rb4.setText(R.string.p3_op4);
                if(preferenciasParticipante.getInt("p3",0)!=0){
                    Toast.makeText(MainActivityPregunta.this, "Pregunta número 3 ya fué registrada" , Toast.LENGTH_LONG).show();
                    rb1.setVisibility(View.GONE);
                    rb2.setVisibility(View.GONE);
                    rb3.setVisibility(View.GONE);
                    rb4.setVisibility(View.GONE);
                    botonEnviarRespuesta.setVisibility(View.GONE);
                    botonVisitarOtroPunto.setVisibility(View.VISIBLE);
                }
                break;
            case 4:
                pregunta.setText(R.string.pregunta4);
                rb1.setText(R.string.p4_op1);
                rb2.setText(R.string.p4_op2);
                rb3.setText(R.string.p4_op3);
                rb4.setText(R.string.p4_op4);
                if(preferenciasParticipante.getInt("p4",0)!=0){
                    Toast.makeText(MainActivityPregunta.this, "Pregunta número 4 ya fué registrada" , Toast.LENGTH_LONG).show();
                    rb1.setVisibility(View.INVISIBLE);
                    rb2.setVisibility(View.INVISIBLE);
                    rb3.setVisibility(View.INVISIBLE);
                    rb4.setVisibility(View.INVISIBLE);
                    botonEnviarRespuesta.setVisibility(View.INVISIBLE);
                    botonVisitarOtroPunto.setVisibility(View.VISIBLE);
                }
                break;
            case 5:
                pregunta.setText(R.string.pregunta5);
                rb1.setText(R.string.p5_op1);
                rb2.setText(R.string.p5_op2);
                rb3.setText(R.string.p5_op3);
                rb4.setText(R.string.p5_op4);
                if(preferenciasParticipante.getInt("p5",0)!=0){
                    Toast.makeText(MainActivityPregunta.this, "Pregunta número 5 ya fué registrada" , Toast.LENGTH_LONG).show();
                    rb1.setVisibility(View.INVISIBLE);
                    rb2.setVisibility(View.INVISIBLE);
                    rb3.setVisibility(View.INVISIBLE);
                    rb4.setVisibility(View.INVISIBLE);
                    botonEnviarRespuesta.setVisibility(View.INVISIBLE);
                    botonVisitarOtroPunto.setVisibility(View.VISIBLE);
                }
                break;
            case 6:
                pregunta.setText(R.string.pregunta6);
                rb1.setText(R.string.p6_op1);
                rb2.setText(R.string.p6_op2);
                rb3.setText(R.string.p6_op3);
                rb4.setText(R.string.p6_op4);
                if(preferenciasParticipante.getInt("p6",0)!=0){
                    Toast.makeText(MainActivityPregunta.this, "Pregunta número 6 ya fué registrada" , Toast.LENGTH_LONG).show();
                    rb1.setVisibility(View.INVISIBLE);
                    rb2.setVisibility(View.INVISIBLE);
                    rb3.setVisibility(View.INVISIBLE);
                    rb4.setVisibility(View.INVISIBLE);
                    botonEnviarRespuesta.setVisibility(View.INVISIBLE);
                    botonVisitarOtroPunto.setVisibility(View.VISIBLE);
                }
                break;
            case 7:
                pregunta.setText(R.string.pregunta7);
                rb1.setText(R.string.p7_op1);
                rb2.setText(R.string.p7_op2);
                rb3.setText(R.string.p7_op3);
                rb4.setText(R.string.p7_op4);
                if(preferenciasParticipante.getInt("p7",0)!=0){
                    Toast.makeText(MainActivityPregunta.this, "Pregunta número 7 ya fué registrada" , Toast.LENGTH_LONG).show();
                    rb1.setVisibility(View.INVISIBLE);
                    rb2.setVisibility(View.INVISIBLE);
                    rb3.setVisibility(View.INVISIBLE);
                    rb4.setVisibility(View.INVISIBLE);
                    botonEnviarRespuesta.setVisibility(View.INVISIBLE);
                    botonVisitarOtroPunto.setVisibility(View.VISIBLE);
                }
                break;
            case 8:
                pregunta.setText(R.string.pregunta8);
                rb1.setText(R.string.p8_op1);
                rb2.setText(R.string.p8_op2);
                rb3.setText(R.string.p8_op3);
                rb4.setText(R.string.p8_op4);
                if(preferenciasParticipante.getInt("p8",0)!=0){
                    Toast.makeText(MainActivityPregunta.this, "Pregunta número 8 ya fué registrada" , Toast.LENGTH_LONG).show();
                    rb1.setVisibility(View.GONE);
                    rb2.setVisibility(View.GONE);
                    rb3.setVisibility(View.GONE);
                    rb4.setVisibility(View.GONE);
                    botonEnviarRespuesta.setVisibility(View.GONE);
                    botonVisitarOtroPunto.setVisibility(View.VISIBLE);
                }
                break;
            case 9:
                pregunta.setText(R.string.pregunta9);
                rb1.setText(R.string.p9_op1);
                rb2.setText(R.string.p9_op2);
                rb3.setText(R.string.p9_op3);
                rb4.setText(R.string.p9_op4);
                if(preferenciasParticipante.getInt("p9",0)!=0){
                    Toast.makeText(MainActivityPregunta.this, "Pregunta número 9 ya fué registrada" , Toast.LENGTH_LONG).show();
                    rb1.setVisibility(View.GONE);
                    rb2.setVisibility(View.GONE);
                    rb3.setVisibility(View.GONE);
                    rb4.setVisibility(View.GONE);
                    botonEnviarRespuesta.setVisibility(View.GONE);
                    botonVisitarOtroPunto.setVisibility(View.VISIBLE);
                }
                break;
            case 10:
                pregunta.setText(R.string.pregunta10);
                rb1.setText(R.string.p10_op1);
                rb2.setText(R.string.p10_op2);
                rb3.setText(R.string.p10_op3);
                rb4.setText(R.string.p10_op4);
                if(preferenciasParticipante.getInt("p10",0)!=0){
                    Toast.makeText(MainActivityPregunta.this, "Pregunta número 10 ya fué registrada" , Toast.LENGTH_LONG).show();
                    rb1.setVisibility(View.GONE);
                    rb2.setVisibility(View.GONE);
                    rb3.setVisibility(View.GONE);
                    rb4.setVisibility(View.GONE);
                    botonEnviarRespuesta.setVisibility(View.GONE);
                    botonVisitarOtroPunto.setVisibility(View.VISIBLE);
                }
                break;
            case 11:
                pregunta.setText(R.string.pregunta11);
                rb1.setVisibility(View.GONE);
                rb2.setVisibility(View.GONE);
                rb3.setVisibility(View.GONE);
                rb4.setVisibility(View.GONE);
                respuesta1113.setVisibility(View.VISIBLE);
                if(preferenciasParticipante.getInt("p11",0)!=0){
                    Toast.makeText(MainActivityPregunta.this, "Pregunta número 11 ya fué registrada" , Toast.LENGTH_LONG).show();
                    respuesta1113.setVisibility(View.GONE);
                    botonEnviarRespuesta.setVisibility(View.GONE);
                    botonVisitarOtroPunto.setVisibility(View.VISIBLE);
                }
                break;
            case 12:
                pregunta.setText(R.string.pregunta12);
                rb1.setVisibility(View.GONE);
                rb2.setVisibility(View.GONE);
                rb3.setVisibility(View.GONE);
                rb4.setVisibility(View.GONE);
                botonEnviarRespuesta.setVisibility(View.GONE);
                botonLeerQr.setVisibility(View.VISIBLE);


                if(preferenciasParticipante.getInt("p12",0)!=0){
                    Toast.makeText(MainActivityPregunta.this, "Pregunta número 12 ya fué registrada" , Toast.LENGTH_LONG).show();
                    botonLeerQr.setVisibility(View.GONE);
                    botonEnviarRespuesta.setVisibility(View.GONE);
                    botonVisitarOtroPunto.setVisibility(View.VISIBLE);
                }
                break;

            case 13:
                pregunta.setText(R.string.pregunta13);
                rb1.setVisibility(View.GONE);
                rb2.setVisibility(View.GONE);
                rb3.setVisibility(View.GONE);
                rb4.setVisibility(View.GONE);
                respuesta1113.setVisibility(View.VISIBLE);
                if(preferenciasParticipante.getInt("p13",0)!=0){
                    Toast.makeText(MainActivityPregunta.this, "Pregunta número 13 ya fué registrada" , Toast.LENGTH_LONG).show();
                    respuesta1113.setVisibility(View.GONE);
                    botonEnviarRespuesta.setVisibility(View.GONE);
                    botonVisitarOtroPunto.setVisibility(View.VISIBLE);
                }
                break;
            case 14:
                pregunta.setText(R.string.pregunta14);
                rb1.setVisibility(View.GONE);
                rb2.setVisibility(View.GONE);
                rb3.setVisibility(View.GONE);
                rb4.setVisibility(View.GONE);
                respuesta14.setVisibility(View.VISIBLE);
                if(preferenciasParticipante.getInt("p14",0)!=0){
                    Toast.makeText(MainActivityPregunta.this, "Pregunta número 14 ya fué registrada" , Toast.LENGTH_LONG).show();
                    respuesta14.setVisibility(View.GONE);
                    botonEnviarRespuesta.setVisibility(View.GONE);
                    botonVisitarOtroPunto.setVisibility(View.VISIBLE);
                }
                break;

            default:
                break;


        }

    }




    private void registrarRespuesta() {

        /*dialogoRegistrarRespuesta=new ProgressDialog(MainActivityPregunta.this);
        dialogoRegistrarRespuesta.setMessage("Registrando Respuesta...");
        dialogoRegistrarRespuesta.show();*/

        request= Volley.newRequestQueue(MainActivityPregunta.this);

        ipServidor = getString(R.string.ipServidor);
        url=ipServidor+"registrarPregunta.php";
        stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //dialogoRegistrarRespuesta.hide();
                if (response.trim().equalsIgnoreCase("registro")) {
                    Toast.makeText(MainActivityPregunta.this, "Su respuesta se registró con éxito", Toast.LENGTH_LONG).show();
                    SharedPreferences.Editor editor=preferenciasParticipante.edit();
                    editor.putInt("puntoActual",numeroPreguntasrespondidas+1);
                    editor.putInt(cargarPreguntaRespondidaPreferecias(),idPregunta);
                    editor.putInt("puntaje",puntaje+puntos);

                    System.out.println("Devolvió como pregunta Shared preferences lo siguinte: ------"+cargarPreguntaRespondidaPreferecias()+"***********************************-----------------------------");
                    editor.apply();

                    System.out.println("NUMERO DE PREGUNTA--------------------------------------------------------:"+numeroPreguntasrespondidas);
                    //sI EL PARTICIPANTE YA RESPONDIO LAS 14 PREGUNTAS EL SISTEMA MUESTRA LA PANTALLA DE FINALIZACIÓN Y LO ENVÍA AL MENÚ
                    if(numeroPreguntasrespondidas==13){
                        Intent irPantalla=new Intent(MainActivityPregunta.this,MainActivityFinal.class);
                        startActivity(irPantalla);

                    }/*else{
                        Intent irPantalla=new Intent(MainActivityPregunta.this,MapsActivity.class);
                        startActivity(irPantalla);

                    }*/

                    finish();



                } else {
                    Toast.makeText(MainActivityPregunta.this, "Lo sentimos...su respuesta no se pudo registrar. Inténtalo nuevamente!", Toast.LENGTH_LONG).show();
                    Log.i("RESPUESTA: ", "" + response);

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivityPregunta.this, "No se ha podido conectar: ", Toast.LENGTH_LONG).show();
                //dialogoRegistrarRespuesta.hide();
            }
        }


        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError{
               Map<String,String> parametros=new HashMap<>();
                parametros.put("respuesta",respuestaSeleccionada);
                parametros.put("idPregunta", String.valueOf(idPregunta));
                parametros.put("idUPB", idUPB);
                //puntos=3;
                parametros.put("puntos", String.valueOf(puntos));
               return parametros;
            }
        };

        request.add(stringRequest);

    }

    private String cargarPreguntaRespondidaPreferecias() {
            String punto="";

        switch (idPregunta){

            case 1:
                punto= "p1";
                break;
            case 2:
                punto= "p2";
                break;
            case 3:
                punto= "p3";
                break;
            case 4:
                punto= "p4";
                break;
            case 5:
                punto= "p5";
                break;
            case 6:
                punto= "p6";
                break;
            case 7:
                punto= "p7";
                break;
            case 8:
                punto= "p8";
                break;
            case 9:
                punto= "p9";
                break;
            case 10:
                punto= "p10";
                break;
            case 11:
                punto= "p11";
                break;
            case 12:
                punto= "p12";
                break;
            case 13:
                punto= "p13";
                break;
            case 14:
                punto= "p14";
                break;

        }

        return punto;
    }


    private View.OnClickListener clicBotonEnviarRespuesta= new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if (idPregunta==11 || idPregunta==13)
            {
                respuestaSeleccionada=respuesta1113.getText().toString();
            }else{
                if (idPregunta==12){
                    respuestaSeleccionada="----------";

                }else{
                    if (idPregunta==14){
                        respuestaSeleccionada=respuesta14.getText().toString();
                    }
                }
            }

            if(respuestaSeleccionada.isEmpty()){
                Toast.makeText(MainActivityPregunta.this, "Debe seleccionar una respuesta", Toast.LENGTH_LONG).show();
            }else{
                registrarRespuesta();
            }




        }
    };

    private View.OnClickListener clicBotonLeerQr= new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            new IntentIntegrator(MainActivityPregunta.this).initiateScan();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult result=IntentIntegrator.parseActivityResult(requestCode,resultCode,data);

        if(result!=null){
            if(result.getContents()!=null) {
                puntos = Integer.parseInt(result.getContents());
                respuestaSeleccionada="----------";
                System.out.println("SIIIIIIIIIIIIIIIIIIIIIII lo esta leyendo" + puntos);
                //botonEnviarRespuesta.setVisibility(View.VISIBLE);
                botonLeerQr.setVisibility(View.GONE);
                registrarRespuesta();
            }else{
            Toast.makeText(MainActivityPregunta.this, "Por favor, intenta nuevamente con tu QR", Toast.LENGTH_LONG).show();
        }
    }
    }

    private View.OnClickListener clicBotonVisitarOtroPunto= new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent irPantalla=new Intent(MainActivityPregunta.this,MapsActivity.class);
            startActivity(irPantalla);
            finish();

        }
    };

    private View.OnClickListener clicRb1= new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Toast.makeText(MainActivityPregunta.this, "Selecciono RB1: "+rb1.getText().toString(), Toast.LENGTH_LONG).show();
            respuestaSeleccionada=rb1.getText().toString();
            calificarRespuesta(1,idPregunta);
        }
    };



    private View.OnClickListener clicRb2= new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Toast.makeText(MainActivityPregunta.this, "Selecciono RB2: "+rb2.getText().toString(), Toast.LENGTH_LONG).show();
            respuestaSeleccionada=rb2.getText().toString();
            calificarRespuesta(2,idPregunta);
        }
    };

    private View.OnClickListener clicRb3= new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Toast.makeText(MainActivityPregunta.this, "Selecciono RB3: "+rb3.getText().toString(), Toast.LENGTH_LONG).show();
            respuestaSeleccionada=rb3.getText().toString();
            calificarRespuesta(3,idPregunta);
        }
    };

    private View.OnClickListener clicRb4= new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Toast.makeText(MainActivityPregunta.this, "Selecciono RB4: "+rb4.getText().toString(), Toast.LENGTH_LONG).show();
            respuestaSeleccionada=rb4.getText().toString();
            calificarRespuesta(4,idPregunta);
            respuestaSeleccionada="----------";
        }
    };

    private void calificarRespuesta(int respuestaRadioButon, int idPregunta)
    {
        switch (respuestaRadioButon)
        {
            case 1:
                    switch (idPregunta)
                    {
                        case 2:
                            puntos=5;
                            break;
                        case 6:
                            puntos=5;
                            break;
                        default:
                            puntos=2;
                            break;
                    }
             break;
            case 2:
                switch (idPregunta)
                {
                    case 1:
                        puntos=5;
                        break;
                    case 5:
                        puntos=5;
                        break;
                    case 9:
                        puntos=5;
                        break;
                    default:
                        puntos=2;
                        break;
                }
                break;
            case 3:
                switch (idPregunta)
                {
                    case 4:
                        puntos=5;
                        break;
                    case 8:
                        puntos=5;
                        break;
                    case 10:
                        puntos=5;
                        break;
                    default:
                        puntos=2;
                        break;
                }
                break;
            case 4:
                switch (idPregunta)
                {
                    case 3:
                        puntos=5;
                        break;
                    case 7:
                        puntos=5;
                        break;
                    default:
                        puntos=2;
                        break;
                }
                break;
        }
    }
}
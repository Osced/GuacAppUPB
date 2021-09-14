package com.upb.laguaca;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class MainActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {

    private ImageView imagenPrincipalGuaca;
    private Button botonJugarGuaca;
    //private ProgressDialog dialogoEspera;
    private String ipServidor;
    private RequestQueue request;
    private JsonObjectRequest jsonObjectRequest;

    /*private LocalDate fechaActual;
    private LocalDate fechaInicio;*/
    private int fechaActual;
    private int fechaInicio;

    /*private LocalTime horaInicio;
    private LocalTime horaFin;
    private LocalTime horaActual;*/
    private int horaInicio;
    private int horaFin;
    private int horaActual;
    private int minActual;//minutos actuales
    private int minInicio;
    private int minFinal;


    //Variables del participante
    private String nombreParticipante;
    private String idUPB;
    private int puntoActual;//Hace referencia a la cantidad de preguntas respondidas por el participante
    private int puntaje=0; //puntos acumulados del participante según el número de respuestas respondidas
    private int periodoInicio;
    private Window ventana;
    private GregorianCalendar f;



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        ventana=getWindow();
        ventana.setStatusBarColor(Color.parseColor("#790E03"));
        //Configuración de elementos gráficos y variables
        imagenPrincipalGuaca= findViewById(R.id.imageViewPrincipal);
        //imagenPrincipalGuaca.setImageResource(R.drawable.tesoro);
        request= Volley.newRequestQueue(MainActivity.this);
        botonJugarGuaca=findViewById(R.id.buttonJugarGuaca);
        botonJugarGuaca.setOnClickListener(clicBotonJugarGauca);

        //Configuración de las variables de fecha de inicio y horarios de juego de La Guaca
        /*fechaInicio=LocalDate.of(2020, Month.SEPTEMBER, 14);
        fechaActual=LocalDate.now();
        horaActual = LocalTime.now();*/

        f=new GregorianCalendar();

        //Se habilita La Guaca
        fechaInicio=14;
        fechaActual=f.get(Calendar.DAY_OF_MONTH);


        System.out.println("El dia del mes es:-----------------------------------------------------------888888888888888"+ fechaActual+" ------ ");
        horaActual=f.get(Calendar.HOUR_OF_DAY);
        minActual=f.get(Calendar.MINUTE);
        System.out.println("La hora del dia es:-----------------------------------------------------------"+ horaActual);

        //Método que verifica que la fecha actual coincida con la fecha de inicio del juego
        iniciarJuegoSegunFechaInicio();






     }

     public  boolean esPeriodoEfectivo(Date horaActual, Date horaInicio, Date horaFinal){
         if (horaActual.getTime() == horaInicio.getTime()
                 || horaActual.getTime() == horaFinal.getTime()) {
             return true;
         }

         Calendar date = Calendar.getInstance();
         date.setTime(horaActual);

         Calendar begin = Calendar.getInstance();
         begin.setTime(horaInicio);

         Calendar end = Calendar.getInstance();
         end.setTime(horaFinal);

         if (date.after(begin) && date.before(end)) {
             return true;
         } else {
             return false;
         }

     }

    public void abrirEcoCampus (View v){

        String ECOCAMPUS = "com.UPB.UPB3D";
        PackageManager pm = getPackageManager();
        Intent intent = pm.getLaunchIntentForPackage(ECOCAMPUS);

        if (intent != null) {

            startActivity(intent);

        } else {

            Intent i = new Intent(android.content.Intent.ACTION_VIEW);
            i.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.UPB.UPB3D"));
            Toast.makeText(this, "...instala nuestra Aplicación EcoCampus UPB - Tour Virtual", Toast.LENGTH_SHORT).show();
            startActivity(i);
        }
     }

    public void abrirInstagram (View v){

        String INSTAGRAM = "com.instagram.android";
        PackageManager pm = getPackageManager();
        Intent intent = pm.getLaunchIntentForPackage(INSTAGRAM);

        if (intent != null) {
            //Post??
            Uri uri = Uri.parse("http://instagram.com/_u/upbcolombia");
            Intent like = new Intent(Intent.ACTION_VIEW, uri);
            like.setPackage(INSTAGRAM);

            try {
                Toast.makeText(this, "Bienvenido a la comunidad @upbcolombia en Instagram", Toast.LENGTH_SHORT).show();
                startActivity(like);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://instagram.com/upbcolombia")));
            }
        } else {

            Intent i = new Intent(android.content.Intent.ACTION_VIEW);
            i.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.instagram.android"));
            Toast.makeText(this, "...sígue a nuestra comunidad @upbcolombia en Instagram", Toast.LENGTH_SHORT).show();
            startActivity(i);
        }

    }


    /*@RequiresApi(api = Build.VERSION_CODES.O)
    private void iniciarJuegoSegunFechaInicio() {

        if(Period.between(fechaInicio,fechaActual.now()).getDays()==0){

            botonJugarGuaca.setVisibility(View.VISIBLE);

        }else{

            botonJugarGuaca.setVisibility(View.INVISIBLE);
            Toast.makeText(MainActivity.this, "Lo sentimos, pronto podrás JUGAR La GUACA" , Toast.LENGTH_LONG).show();
        }

    }*/

    private void iniciarJuegoSegunFechaInicio() {

        if(fechaActual==fechaInicio){

            botonJugarGuaca.setVisibility(View.VISIBLE);


        }else{

            botonJugarGuaca.setVisibility(View.INVISIBLE);
            Toast.makeText(MainActivity.this, "Lo sentimos, pronto podrás JUGAR La GUACA" , Toast.LENGTH_LONG).show();
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    //Método que permite iniciar el juego, según el periódo de juego destinado a cada participante


    private void iniciarJuegoSegunPeriodo( JSONObject jsonObject) {

        String format = "HH:mm";
        Date nowTime = null;
        try {
            //nowTime = new SimpleDateFormat(format).parse("21:29");
            nowTime = new SimpleDateFormat(format).parse(horaActual+":"+minActual);
            //Date startTime = new SimpleDateFormat(format).parse("19:30");
            Date startTime = new SimpleDateFormat(format).parse(horaInicio+":"+minInicio);
            //Date endTime = new SimpleDateFormat(format).parse("21:30");
            Date endTime = new SimpleDateFormat(format).parse(horaFin+":"+minFinal);
            System.out.println("Prueba de periodo"+esPeriodoEfectivo(nowTime, startTime, endTime));
            System.out.println("actual: "+nowTime+"  inicio: "+startTime+"  final: "+endTime);
            if(esPeriodoEfectivo(nowTime, startTime, endTime)){

                Toast.makeText(MainActivity.this, "Bienvenido a la Guaca 2020: " + jsonObject.optString("nombres") + " - " + jsonObject.optString("id_UPB"), Toast.LENGTH_LONG).show();
                Intent irPantalla = new Intent(MainActivity.this, MapsActivity.class);
                //Bundle participanteGuaga=new Bundle();
                //participanteGuaga.putString("nombreParticipante",jsonObject.optString("nombres") );
                //participanteGuaga.putString("idUPB",jsonObject.optString("id_UPB") );
                //participanteGuaga.putInt("puntoActual",jsonObject.optInt("punto_actual") );
                //irPantalla.putExtras(participanteGuaga);
                //Método que llena el archivo shared preferences con los datos del participante traidos de la BD, cuando un participante está registrado
                llenarPreferencias(jsonObject);
                startActivity(irPantalla);

            }else{
                botonJugarGuaca.setVisibility(View.INVISIBLE);
                Toast.makeText(MainActivity.this, "Lo sentimos aún no es tu turno para JUGAR" , Toast.LENGTH_LONG).show();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //if((horaActual>=horaInicio) && (horaActual<=horaFin)){





    }




    /*private void iniciarJuegoSegunPeriodo( JSONObject jsonObject) {


            if(horaActual.isAfter(horaInicio) && horaActual.isBefore(horaFin)){

                Toast.makeText(MainActivity.this, "Bienvenido a la Guaca: "+ jsonObject.optString("nombres") +" - "+ jsonObject.optString("id_UPB"), Toast.LENGTH_LONG).show();
                Intent irPantalla=new Intent(MainActivity.this,MapsActivity.class);
                //Bundle participanteGuaga=new Bundle();
                //participanteGuaga.putString("nombreParticipante",jsonObject.optString("nombres") );
                //participanteGuaga.putString("idUPB",jsonObject.optString("id_UPB") );
                //participanteGuaga.putInt("puntoActual",jsonObject.optInt("punto_actual") );
                //irPantalla.putExtras(participanteGuaga);
                //Método que llena el archivo shared preferences con los datos del participante traidos de la BD, cuando un participante está registrado
                llenarPreferencias(jsonObject);
                startActivity(irPantalla);
            }else{
                botonJugarGuaca.setVisibility(View.INVISIBLE);
                Toast.makeText(MainActivity.this, "Lo sentimos aún no es tu turno para JUGAR " , Toast.LENGTH_LONG).show();
            }



    }*/
    //Método pra cargar la información del participante registrado en la BD, en un objeto SharedPreferences
    private void llenarPreferencias(JSONObject jsonObject) {
        SharedPreferences preferenciasParticipante=getSharedPreferences("InfoParticipante", Context.MODE_PRIVATE);
        System.out.println(" Usuario: "+ preferenciasParticipante.getString("nombreParticipante","noHayUsuario")+ "--------------------------Respuesta P1: "+preferenciasParticipante.getInt("p1",0));
        nombreParticipante=jsonObject.optString("nombres");

        System.out.println("p1: "+jsonObject.optString("p1")+"  p2: "+jsonObject.optString("p2")+"  p3: "+jsonObject.optString("p3")+"  p4: "+jsonObject.optString("p4")+"  p5: "+jsonObject.optString("p5")+
                "  p6: "+jsonObject.optString("p6")+"  p7: "+jsonObject.optString("p7")+"  p8: "+jsonObject.optString("p8")+"  p9: "+jsonObject.optString("p9")+"  p10: "+jsonObject.optString("p10"));
        SharedPreferences.Editor editor=preferenciasParticipante.edit();
        editor.putString("nombreParticipante",nombreParticipante);
        editor.putInt("puntoActual",puntoActual);//Es el numero de preguntas respondidas
        editor.putInt("puntaje",puntaje);
        editor.putInt("periodoInicio",Integer.parseInt(jsonObject.optString("periodo")));
        editor.putInt("p1",Integer.parseInt(jsonObject.optString("p1")));//
        editor.putInt("p2",Integer.parseInt(jsonObject.optString("p2")));//
        editor.putInt("p3",Integer.parseInt(jsonObject.optString("p3")));//
        editor.putInt("p4",Integer.parseInt(jsonObject.optString("p4")));//
        editor.putInt("p5",Integer.parseInt(jsonObject.optString("p5")));//
        editor.putInt("p6",Integer.parseInt(jsonObject.optString("p6")));//
        editor.putInt("p7",Integer.parseInt(jsonObject.optString("p7")));//
        editor.putInt("p8",Integer.parseInt(jsonObject.optString("p8")));//
        editor.putInt("p9",Integer.parseInt(jsonObject.optString("p9")));//
        editor.putInt("p10",Integer.parseInt(jsonObject.optString("p10")));//
        editor.putInt("p11",Integer.parseInt(jsonObject.optString("p11")));//
        editor.putInt("p12",Integer.parseInt(jsonObject.optString("p12")));//
        editor.putInt("p13",Integer.parseInt(jsonObject.optString("p13")));//
        editor.putInt("p14",Integer.parseInt(jsonObject.optString("p14")));//
        editor.putInt("p15",Integer.parseInt(jsonObject.optString("p15")));//
        editor.putInt("p16",Integer.parseInt(jsonObject.optString("p16")));//
        editor.putInt("p17",Integer.parseInt(jsonObject.optString("p17")));//
        editor.putInt("p18",Integer.parseInt(jsonObject.optString("p18")));//
        editor.putInt("p19",Integer.parseInt(jsonObject.optString("p19")));//
        editor.putInt("p20",Integer.parseInt(jsonObject.optString("p20")));//
        editor.putString("idUPB",idUPB);

        editor.commit();

        System.out.println("Desde preferencias p1: "+preferenciasParticipante.getInt("p1",102)+"  p2: "+preferenciasParticipante.getInt("p2",102)+"  p3: "+preferenciasParticipante.getInt("p3",102)+"  p4: "+preferenciasParticipante.getInt("p4",102)+"  p5: "+preferenciasParticipante.getInt("p5",102)+
                "  p6: "+preferenciasParticipante.getInt("p6",102)+"  p7: "+preferenciasParticipante.getInt("p7",102)+"  p8: "+preferenciasParticipante.getInt("p8",102)+"  p9: "+preferenciasParticipante.getInt("p9",102)+"  p10: "+preferenciasParticipante.getInt("p10",102));


    }



    //Método para configurar el periódo de juego de un participante según el turno destinado desde la BD (campo punto_actual)
    private void configurarPeriodo() {
        minInicio=30;
        minFinal=30;
        switch(periodoInicio){

            case 1:
                horaInicio=1;
                horaFin=23;
                break;

            case 8:
                horaInicio=8;
                horaFin=10;
                break;

            case 12:
                horaInicio=12;
                horaFin=14;
                break;

            case 16:
                horaInicio=16;
                horaFin=18;
                break;



            case 20:
                horaInicio=20;
                horaFin=23;
                break;

        }
    }
   /* @RequiresApi(api = Build.VERSION_CODES.O)
    private void configurarPeriodo() {

        switch(periodoInicio){
            case 8:
                horaInicio=LocalTime.of(8,00);
                horaFin=LocalTime.of(11,00);
                break;
            case 11:
                horaInicio=LocalTime.of(11,00);
                horaFin=LocalTime.of(13,00);
                break;

            case 13:
                horaInicio=LocalTime.of(13,00);
                horaFin=LocalTime.of(23,59);
                break;

        }
    }*/


    //Método de respuesta al utilizar la camára para lectura código QR
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        IntentResult result=IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result!=null){
            if(result.getContents()!=null){

                idUPB=result.getContents().toString();
                cargarWebService();
            }else{
                Toast.makeText(MainActivity.this, "Por favor, intenta nuevamente con tu QR", Toast.LENGTH_LONG).show();
            }
        }
    }

    //Método que maneja el evento clic del botón Jugar la Guaca
    private View.OnClickListener clicBotonJugarGauca= new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //Toast.makeText(MainActivity.this, "Ingresando a La Guaca", Toast.LENGTH_LONG).show();
            new IntentIntegrator(MainActivity.this).initiateScan();

        }
    };

    //Método para realizar la consulta en la base de datos según el código QR escaneado
    private void cargarWebService() {

        //dialogoEspera=new ProgressDialog(MainActivity.this);
        //dialogoEspera.setMessage("Estamos validando su ingreso a La Guaca...");
        //dialogoEspera.show();

        ipServidor=getString(R.string.ipServidor);

        String url=ipServidor+"consultarParticipante.php?idUPB="+idUPB;

        System.out.println("URL:----------------- "+url);
        jsonObjectRequest=new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        request.add(jsonObjectRequest);
        botonJugarGuaca.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        //dialogoEspera.hide();
        Toast.makeText(MainActivity.this, "Hubo un error al intentar conectarse con la BD: "+ error.toString(), Toast.LENGTH_LONG).show();
        System.out.println();
        Log.d("Error: ",error.toString());
        botonJugarGuaca.setVisibility(View.VISIBLE);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onResponse(JSONObject response) {
        //dialogoEspera.hide();
        //Toast.makeText(MainActivity.this, "Conexión Exitosa "+ response, Toast.LENGTH_LONG).show();

        JSONArray json=response.optJSONArray("participante");
        JSONObject jsonObject=null;

        try {
            jsonObject=json.getJSONObject(0);

            String idUPB=jsonObject.optString("id_UPB");
            System.out.println("ACA QUE"+idUPB);

            if (idUPB.equals("noregistrado"))
            {
                Toast.makeText(MainActivity.this, "ID UPB No Registrado", Toast.LENGTH_LONG).show();

            }else{
                puntoActual=Integer.parseInt(jsonObject.optString("punto_actual"));
                puntaje=Integer.parseInt(jsonObject.optString("puntaje"));
                System.out.println("Punto Actual en MainActivity--------------------------------------------------------------------"+puntoActual);
                //SI EL PARTICIPANTE YA HA RESPONDIDO LAS 15 PREGUNTAS EL SISTEMA NO LO DEJA INGRESAR
                if(puntoActual>=14){
                    Toast.makeText(MainActivity.this, "Haz terminado el juego de LA GUACA", Toast.LENGTH_LONG).show();
                }else{
                    periodoInicio= Integer.parseInt(jsonObject.optString("periodo"));
                    //Método que configura los periodos de tiempo permitidos para jugar la guaca
                    configurarPeriodo();
                    //Método revisa en cuál periodo de tiempo (8-11, 12-3- 4-7) puede jugar el participante
                    iniciarJuegoSegunPeriodo(jsonObject);
                }

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
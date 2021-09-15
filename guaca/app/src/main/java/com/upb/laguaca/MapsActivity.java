package com.upb.laguaca;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;



public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private int banderaInicio=1;

    private GoogleMap mMap;
    private String nombreParticipante;
    private Marker p1;
    private Marker p2;
    private Marker p3;
    private Marker p4;
    private Marker p5;
    private Marker p6;
    private Marker p7;
    private Marker p8;
    private Marker p9;
    private Marker p10;
    private Marker p11;
    private Marker p12;
    private Marker p13;
    private Marker p14;
    

    private String idUPB;

    private SharedPreferences preferenciasParticipante;
    private SupportMapFragment mapFragment;

    private int puntoActual;
    private Window ventana;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);


        ventana=getWindow();
        ventana.setStatusBarColor(Color.parseColor("#790E03"));


    }

    private void deshabilitarPuntos() {
        SharedPreferences preferenciasParticipante=getSharedPreferences("InfoParticipante", Context.MODE_PRIVATE);
        //System.out.println("-----------------***************************////////////////////////Esta entrando en Deshabilitar 2");
        //System.out.println("Desde ACTIVIDAD DEL MAPA  p1: "+preferenciasParticipante.getInt("p1",102)+"  p2: "+preferenciasParticipante.getInt("p2",102)+"  p3: "+preferenciasParticipante.getInt("p3",102)+"  p4: "+preferenciasParticipante.getInt("p4",102)+"  p5: "+preferenciasParticipante.getInt("p5",102)+
                //"  p6: "+preferenciasParticipante.getInt("p6",102)+"  p7: "+preferenciasParticipante.getInt("p7",102)+"  p8: "+preferenciasParticipante.getInt("p8",102)+"  p9: "+preferenciasParticipante.getInt("p9",102)+"  p10: "+preferenciasParticipante.getInt("p10",102));


        if(preferenciasParticipante.getInt("p1",0)!=0){
            p1.setIcon(generateBitmapDescriptorFromRes(MapsActivity.this, R.mipmap.marcador_final));
            p1.setTitle("x");

        }else{
            p1.setIcon(generateBitmapDescriptorFromRes(MapsActivity.this, R.mipmap.marcador_inicio));
        }

        if(preferenciasParticipante.getInt("p2",0)!=0){
            p2.setIcon(generateBitmapDescriptorFromRes(MapsActivity.this, R.mipmap.marcador_final));
            p2.setTitle("x");
            //System.out.println("eNTRO A DIFERENTE DE CERO--------------------------------------------------------------------");
        }else{
            p2.setIcon(generateBitmapDescriptorFromRes(MapsActivity.this, R.mipmap.marcador_inicio));
            //System.out.println("------------------------------------eNTRO A IGUAL A CERO");
        }

        if(preferenciasParticipante.getInt("p3",0)!=0){
            p3.setIcon(generateBitmapDescriptorFromRes(MapsActivity.this, R.mipmap.marcador_final));
            p3.setTitle("x");
        }else{
            p3.setIcon(generateBitmapDescriptorFromRes(MapsActivity.this, R.mipmap.marcador_inicio));
        }

        if(preferenciasParticipante.getInt("p4",0)!=0){
            p4.setIcon(generateBitmapDescriptorFromRes(MapsActivity.this, R.mipmap.marcador_final));
            p4.setTitle("x");
        }else{
            p4.setIcon(generateBitmapDescriptorFromRes(MapsActivity.this, R.mipmap.marcador_inicio));
        }

        if(preferenciasParticipante.getInt("p5",0)!=0){
            p5.setIcon(generateBitmapDescriptorFromRes(MapsActivity.this, R.mipmap.marcador_final));
            p5.setTitle("x");
        }else{
            p5.setIcon(generateBitmapDescriptorFromRes(MapsActivity.this, R.mipmap.marcador_inicio));
        }

        if(preferenciasParticipante.getInt("p6",0)!=0){
            p6.setIcon(generateBitmapDescriptorFromRes(MapsActivity.this, R.mipmap.marcador_final));
            p6.setTitle("x");
        }else{
            p6.setIcon(generateBitmapDescriptorFromRes(MapsActivity.this, R.mipmap.marcador_inicio));
        }

        if(preferenciasParticipante.getInt("p7",0)!=0){
            p7.setIcon(generateBitmapDescriptorFromRes(MapsActivity.this, R.mipmap.marcador_final));
            p7.setTitle("x");
        }else{
            p7.setIcon(generateBitmapDescriptorFromRes(MapsActivity.this, R.mipmap.marcador_inicio));
        }

        if(preferenciasParticipante.getInt("p8",0)!=0){
            p8.setIcon(generateBitmapDescriptorFromRes(MapsActivity.this, R.mipmap.marcador_final));
            p8.setTitle("x");
        }else{
            p8.setIcon(generateBitmapDescriptorFromRes(MapsActivity.this, R.mipmap.marcador_inicio));
        }

        if(preferenciasParticipante.getInt("p9",0)!=0){
            p9.setIcon(generateBitmapDescriptorFromRes(MapsActivity.this, R.mipmap.marcador_final));
            p9.setTitle("x");
        }else{
            p9.setIcon(generateBitmapDescriptorFromRes(MapsActivity.this, R.mipmap.marcador_inicio));
        }

        if(preferenciasParticipante.getInt("p10",0)!=0){
            p10.setIcon(generateBitmapDescriptorFromRes(MapsActivity.this, R.mipmap.marcador_final));
            p10.setTitle("x");
        }else{
            p10.setIcon(generateBitmapDescriptorFromRes(MapsActivity.this, R.mipmap.marcador_inicio));
        }

        if(preferenciasParticipante.getInt("p11",0)!=0){
            p11.setIcon(generateBitmapDescriptorFromRes(MapsActivity.this, R.mipmap.marcador_final));
            p11.setTitle("x");
        }else{
            p11.setIcon(generateBitmapDescriptorFromRes(MapsActivity.this, R.mipmap.marcador_inicio));
        }

        if(preferenciasParticipante.getInt("p12",0)!=0){
            p12.setIcon(generateBitmapDescriptorFromRes(MapsActivity.this, R.mipmap.marcador_final));
            p12.setTitle("x");
        }else{
            p12.setIcon(generateBitmapDescriptorFromRes(MapsActivity.this, R.mipmap.marcador_inicio));
        }

        if(preferenciasParticipante.getInt("p13",0)!=0){
            p13.setIcon(generateBitmapDescriptorFromRes(MapsActivity.this, R.mipmap.marcador_final));
            p13.setTitle("x");
        }else{
            p13.setIcon(generateBitmapDescriptorFromRes(MapsActivity.this, R.mipmap.marcador_inicio));
        }

        if(preferenciasParticipante.getInt("p14",0)!=0){
            p14.setIcon(generateBitmapDescriptorFromRes(MapsActivity.this, R.mipmap.marcador_final));
            p14.setTitle("x");
        }else{
            p14.setIcon(generateBitmapDescriptorFromRes(MapsActivity.this, R.mipmap.marcador_inicio));
        }
        

    }

    //Método para cargar los datos del participante desde el objeto SharedPreferences
    private void cargarPreferenciasParticipante() {
        //System.out.println("PASO POR ACA 66666666666666666666666666666666666-----------------------------"+banderaInicio);

        preferenciasParticipante=getSharedPreferences("InfoParticipante", Context.MODE_PRIVATE);
        nombreParticipante=preferenciasParticipante.getString("nombreParticipante","noHayUsuario");
        puntoActual=preferenciasParticipante.getInt("puntoActual",0);
        idUPB=preferenciasParticipante.getString("idUPB","NoRegistrado");

        //Toast.makeText(MapsActivity.this,"BIENVENIDO a LA GUACA "+ nombreParticipante+ "- Id UPB: "+ idUPB,Toast.LENGTH_LONG).show();

        if(puntoActual==0)//Se carga una modal con las indicaciones del juego la primera vez que el usuario ingresa al juego
        {
            /*AlertDialog.Builder modal=new AlertDialog.Builder(MapsActivity.this);
            modal.setTitle("Pregunta 1");
            modal.setMessage("En esta prueba debes ir al punto y....");
            modal.setNeutralButton("Cerrar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            modal.create();
            modal.show();*/
            mostrarDialogoPerzonalizado();
        }

        //System.out.println("PASO POR ACA 66666666666666666666666666666666666-----------------------------Punto actual:"+puntoActual);
    }

    private void mostrarDialogoPerzonalizado() {
        AlertDialog.Builder builder= new AlertDialog.Builder(MapsActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View view=inflater.inflate(R.layout.alert_dialog_personalizado,null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();
        Button botonCancelar = view.findViewById(R.id.buttonCerrarDialog);
        botonCancelar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
    }
    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */


    @Override
    public void onMapReady(GoogleMap googleMap) {

        //System.out.println("PASO POR ACA 77777777777777777777777777777777777777777777------------------------------"+banderaInicio);
        mMap = googleMap;

        boolean preguntaCargada=false;//Para saber si ya se encontró una pregunta a cargar, la pregunta se carga según un número random y que no este respondida

        LatLng upbColombia = new LatLng(6.2420032, -75.5896037);
        LatLng lp1Upb = new LatLng(6.2420032, -75.5896037);
        LatLng lp2Upb = new LatLng(6.240858, -75.590820);
        LatLng lp3Upb = new LatLng(6.244361, -75.589446);
        LatLng lp4Upb = new LatLng(6.241572, -75.591466);
        LatLng lp5Upb = new LatLng(6.241109, -75.589337);
        LatLng lp6Upb = new LatLng(6.242048, -75.591422);
        LatLng lp7Upb = new LatLng(6.243337, -75.590221);
        LatLng lp8Upb = new LatLng(6.241776,  -75.590493);
        LatLng lp9Upb = new LatLng(6.241011, -75.588425);
        LatLng lp10Upb= new LatLng(6.241507, -75.588919);


        LatLng lp11Upb = new LatLng(6.241333, -75.588999);//LivingLab
        LatLng lp12Upb = new LatLng(6.243415, -75.590690);//Cancha Frente Bloque12
        LatLng lp13Upb = new LatLng(6.244361, -75.589446);//Laboratorios
        LatLng lp14Upb = new LatLng(6.244453, -75.589363);//Plazoleta Fundadores



        SharedPreferences preferenciasParticipante=getSharedPreferences("InfoParticipante", Context.MODE_PRIVATE);
        //System.out.println("-----------------***************************////////////////////////Esta entrando en Deshabilitar 2");
        //System.out.println("Desde ACTIVIDAD DEL MAPA  p1: "+preferenciasParticipante.getInt("p1",102)+"  p2: "+preferenciasParticipante.getInt("p2",102)+"  p3: "+preferenciasParticipante.getInt("p3",102)+"  p4: "+preferenciasParticipante.getInt("p4",102)+"  p5: "+preferenciasParticipante.getInt("p5",102)+
                //"  p6: "+preferenciasParticipante.getInt("p6",102)+"  p7: "+preferenciasParticipante.getInt("p7",102)+"  p8: "+preferenciasParticipante.getInt("p8",102)+"  p9: "+preferenciasParticipante.getInt("p9",102)+"  p10: "+preferenciasParticipante.getInt("p10",102));


            if (p1==null){
                System.out.println("ENTRO EN MARCADOR 1 ????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????");
                p1=mMap.addMarker(new MarkerOptions().position(lp1Upb).title("Forúm"));//id=m0
                p1.setVisible(false);
            }

            if (p2==null) {
                System.out.println("ENTRO EN MARCADOR 2 ????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????");
                p2 = mMap.addMarker(new MarkerOptions().position(lp2Upb).title("Portería la 70"));//id=m1
                p2.setVisible(false);
            }

            if (p3==null) {
                System.out.println("ENTRO EN MARCADOR 3 ????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????");
                p3 = mMap.addMarker(new MarkerOptions().position(lp3Upb).title("Asesoría Integral"));//id=m2
                p3.setVisible(false);
            }

            if (p4==null) {
                System.out.println("ENTRO EN MARCADOR 4 ????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????");
                p4 = mMap.addMarker(new MarkerOptions().position(lp4Upb).title("Bloque 8"));//id=m3
                p4.setVisible(false);
            }
            if (p5==null) {
                System.out.println("ENTRO EN MARCADOR 5 ????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????");
                p5 = mMap.addMarker(new MarkerOptions().position(lp5Upb).title("Bloque de canchas y parqueaderos"));//id=m4
                p5.setVisible(false);
            }

            if (p6==null) {
                System.out.println("ENTRO EN MARCADOR 6 ????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????");
                p6 = mMap.addMarker(new MarkerOptions().position(lp6Upb).title("Librería"));//id=m5
                p6.setVisible(false);
            }
            if (p7==null) {
                System.out.println("ENTRO EN MARCADOR 7 ????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????");
                p7 = mMap.addMarker(new MarkerOptions().position(lp7Upb).title("Polideportivo"));//id=m6
                p7.setVisible(false);
            }

            if (p8==null) {
                System.out.println("ENTRO EN MARCADOR 8 ????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????");
                p8 = mMap.addMarker(new MarkerOptions().position(lp8Upb).title("Portería de Unicentro"));//id=m7
                p8.setVisible(false);
            }

            if (p9==null ) {
                System.out.println("ENTRO EN MARCADOR 9 ????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????");
                p9 = mMap.addMarker(new MarkerOptions().position(lp9Upb).title("Canchas de arenilla cerca al bloque 7"));//id=m8
                p9.setVisible(false);
            }

            if (p10==null) {
                System.out.println("ENTRO EN MARCADOR 10 ????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????");
                p10 = mMap.addMarker(new MarkerOptions().position(lp10Upb).title("Biblioteca"));//id=m9
                p10.setVisible(false);
            }

            if (p11==null ) {
                System.out.println("ENTRO EN MARCADOR 11 ????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????");
                p11 = mMap.addMarker(new MarkerOptions().position(lp11Upb).title("Living Lab"));//id=m10
                p11.setVisible(false);
            }

            if (p12==null ) {
                System.out.println("ENTRO EN MARCADOR 12 ????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????");
                p12 = mMap.addMarker(new MarkerOptions().position(lp12Upb).title("Cancha frente Bloque 12"));//id=m9
                p12.setVisible(false);
            }

            if (p13==null ) {
                System.out.println("ENTRO EN MARCADOR 13 ????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????");
                p13 = mMap.addMarker(new MarkerOptions().position(lp13Upb).title("Laboratorios"));//id=m9
                p13.setVisible(false);
            }

            if (p14==null ) {
                System.out.println("ENTRO EN MARCADOR 14 ????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????");
                p14 = mMap.addMarker(new MarkerOptions().position(lp14Upb).title("Plazoleta Fundadores"));//id=m9
                p14.setVisible(false);
            }



/*
        if (p15==null) {
            p15 = mMap.addMarker(new MarkerOptions().position(lp15).title("Cancha Sintéticas"));//id=m9
        }

        f (p16==null) {
            p16 = mMap.addMarker(new MarkerOptions().position(lp16).title("punto 16"));//id=m9
        }

        if (p17==null) {
            p17 = mMap.addMarker(new MarkerOptions().position(lp17).title("punto 17"));//id=m9
        }

        if (p18==null) {
            p18 = mMap.addMarker(new MarkerOptions().position(lp18).title("punto 18"));//id=m9
        }

        if (p19==null) {
            p19 = mMap.addMarker(new MarkerOptions().position(lp19).title("punto 19"));//id=m9
        }

        if (p20==null) {
            p20 = mMap.addMarker(new MarkerOptions().position(lp20).title("punto 20"));//id=m9
        }*/

        //deshabilitarPuntos();//Método para deshabilitar el tooltip de las preguntas ya respondidas


        //p1Forum=mMap.addMarker(new MarkerOptions().icon(generateBitmapDescriptorFromRes(MapsActivity.this, R.drawable.ic_baseline_location_off_24 )).anchor(0.0f,1.0f).position(upbForum));
        //System.out.println(preguntaCargada+"////////////////////////////////"+puntoActual);
        //System.out.println("PASO POR ACA 777777777777777777777777777777777777-----------------------------Punto actual:"+puntoActual);
        if ( puntoActual<13) {

            while (preguntaCargada == false) {
                int preguntaACargar = (int) (Math.random() * 13 + 1);
                System.out.println("Esta es la pregunta por random " + preguntaACargar);
                if (preferenciasParticipante.getInt("p1", 0) == 0 && preguntaACargar == 1) {
                    p1.setIcon(generateBitmapDescriptorFromRes(MapsActivity.this, R.mipmap.marcador_inicio));
                    p1.setVisible(true);
                    preguntaCargada = true;

                } else {
                    p1.setVisible(false);
                }

                if (preferenciasParticipante.getInt("p2", 0) == 0 && preguntaACargar == 2) {

                    p2.setIcon(generateBitmapDescriptorFromRes(MapsActivity.this, R.mipmap.marcador_inicio));
                    p2.setVisible(true);
                    preguntaCargada = true;
                } else {
                    p2.setVisible(false);
                }

                if (preferenciasParticipante.getInt("p3", 0) == 0 && preguntaACargar == 3) {
                    p3.setIcon(generateBitmapDescriptorFromRes(MapsActivity.this, R.mipmap.marcador_inicio));
                    p3.setVisible(true);
                    preguntaCargada = true;
                } else {
                    p3.setVisible(false);
                }

                if (preferenciasParticipante.getInt("p4", 0) == 0 && preguntaACargar == 4) {
                    p4.setIcon(generateBitmapDescriptorFromRes(MapsActivity.this, R.mipmap.marcador_inicio));
                    p4.setVisible(true);
                    preguntaCargada = true;
                } else {
                    p4.setVisible(false);
                }

                if (preferenciasParticipante.getInt("p5", 0) == 0 && preguntaACargar == 5) {
                    p5.setIcon(generateBitmapDescriptorFromRes(MapsActivity.this, R.mipmap.marcador_inicio));
                    p5.setVisible(true);
                    preguntaCargada = true;
                } else {
                    p5.setVisible(false);
                }

                if (preferenciasParticipante.getInt("p6", 0) == 0 && preguntaACargar == 6) {
                    p6.setIcon(generateBitmapDescriptorFromRes(MapsActivity.this, R.mipmap.marcador_inicio));
                    p6.setVisible(true);
                    preguntaCargada = true;
                } else {
                    p6.setVisible(false);
                }
                if (preferenciasParticipante.getInt("p7", 0) == 0 && preguntaACargar == 7) {
                    p7.setIcon(generateBitmapDescriptorFromRes(MapsActivity.this, R.mipmap.marcador_inicio));
                    p7.setVisible(true);
                    preguntaCargada = true;
                } else {
                    p7.setVisible(false);
                }

                if (preferenciasParticipante.getInt("p8", 0) == 0 && preguntaACargar == 8) {
                    p8.setIcon(generateBitmapDescriptorFromRes(MapsActivity.this, R.mipmap.marcador_inicio));
                    p8.setVisible(true);
                    preguntaCargada = true;
                } else {
                    p8.setVisible(false);
                }

                if (preferenciasParticipante.getInt("p9", 0) == 0 && preguntaACargar == 9) {
                    p9.setIcon(generateBitmapDescriptorFromRes(MapsActivity.this, R.mipmap.marcador_inicio));
                    p9.setVisible(true);
                    preguntaCargada = true;
                } else {
                    p9.setVisible(false);
                }

                if (preferenciasParticipante.getInt("p10", 0) == 0 && preguntaACargar == 10) {
                    p10.setIcon(generateBitmapDescriptorFromRes(MapsActivity.this, R.mipmap.marcador_inicio));
                    p10.setVisible(true);
                    preguntaCargada = true;
                } else {
                    p10.setVisible(false);
                }

                if (preferenciasParticipante.getInt("p11", 0) == 0 && preguntaACargar == 11) {
                    p11.setIcon(generateBitmapDescriptorFromRes(MapsActivity.this, R.mipmap.marcador_inicio));
                    p11.setVisible(true);
                    preguntaCargada = true;
                } else {
                    p11.setVisible(false);
                }

                if (preferenciasParticipante.getInt("p12", 0) == 0 && preguntaACargar == 12) {
                    p12.setIcon(generateBitmapDescriptorFromRes(MapsActivity.this, R.mipmap.marcador_inicio));
                    p12.setVisible(true);
                    preguntaCargada = true;
                } else {
                    p12.setVisible(false);
                }

                if (preferenciasParticipante.getInt("p13", 0) == 0 && preguntaACargar == 13) {
                    p13.setIcon(generateBitmapDescriptorFromRes(MapsActivity.this, R.mipmap.marcador_inicio));
                    p13.setVisible(true);
                    preguntaCargada = true;
                } else {
                    p13.setVisible(false);
                }

            /*if ( preferenciasParticipante.getInt("p14",0)==0 && preguntaACargar==14) {
                p14.setIcon(generateBitmapDescriptorFromRes(MapsActivity.this, R.mipmap.marcador_inicio));
                p14.setVisible(true);
                preguntaCargada=true;
            }else{
                p14.setVisible(false);
            }*/

            }
        }else {
                //Debo poner invisibles todoa los puntos para evitar que la pregunta que sale en la posición 13 vuelva a verse, solo debe quedar visible la pregunta 14
                p1.setVisible(false);
                p2.setVisible(false);
                p3.setVisible(false);
                p4.setVisible(false);
                p5.setVisible(false);
                p6.setVisible(false);
                p7.setVisible(false);
                p8.setVisible(false);
                p9.setVisible(false);
                p10.setVisible(false);
                p11.setVisible(false);
                p12.setVisible(false);
                p13.setVisible(false);

                p14.setIcon(generateBitmapDescriptorFromRes(MapsActivity.this, R.mipmap.marcador_inicio));
                p14.setVisible(true);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lp14Upb, 16));

        }


        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {


                if(!marker.getTitle().equals("x")){
                    //Toast.makeText(MapsActivity.this,"clic en"+marker.getTitle().toString()+ "Id:  "+marker.getId().toString(),Toast.LENGTH_LONG).show();
                    //System.out.println("Marcadores nuevos------------------------------"+marker.getId().toString());
                    //p1Forum.setTitle("");

                cargarIdpreguntaPrueba(marker.getId().toString());//para simular paso de numero pregunta
                Intent irPantalla=new Intent(MapsActivity.this,MainActivityPregunta.class);
                startActivity(irPantalla);
                /*String WHATSAPP = "com.whatsapp";
                PackageManager pm = getPackageManager();
                Intent intent = pm.getLaunchIntentForPackage(WHATSAPP);
                startActivity(intent);
                    cargarIdpreguntaPrueba(marker.getId().toString());//para simular paso de numero pregunta
                    Intent irPantalla=new Intent(MapsActivity.this,MainActivityPregunta.class);
                    startActivity(irPantalla);*/

                }else{
                    System.out.println("Marcadores nuevos XXXXXXXXX------------------------------"+marker.getId().toString());

                }

            }
        });

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(upbColombia, 17));
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
    }




    public static BitmapDescriptor generateBitmapDescriptorFromRes(
            Context context, int resId) {
        Drawable drawable = ContextCompat.getDrawable(context, resId);
        drawable.setBounds(
                0,
                0,
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }




    void cargarIdpreguntaPrueba(String idMarcador){
        SharedPreferences preferenciasParticipante=getSharedPreferences("InfoParticipante", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferenciasParticipante.edit();
        switch (idMarcador){

            case "m0":
                editor.putInt("numeroPregunta",1);
                editor.commit();
                break;
            case "m1":
                editor.putInt("numeroPregunta",2);
                editor.commit();
                break;

            case "m2":
                editor.putInt("numeroPregunta",3);
                editor.commit();
                break;

            case "m3":
                editor.putInt("numeroPregunta",4);
                editor.commit();
                break;

            case "m4":
                editor.putInt("numeroPregunta",5);
                editor.commit();
                break;

            case "m5":
                editor.putInt("numeroPregunta",6);
                editor.commit();
                break;

            case "m6":
                editor.putInt("numeroPregunta",7);
                editor.commit();
                break;

            case "m7":
                editor.putInt("numeroPregunta",8);
                editor.commit();
                break;
            case "m8":
                editor.putInt("numeroPregunta",9);
                editor.commit();
                break;
            case "m9":
                editor.putInt("numeroPregunta",10);
                editor.commit();
                break;

            case "m10":
                editor.putInt("numeroPregunta",11);
                editor.commit();
                break;

            case "m11":
                editor.putInt("numeroPregunta",12);
                editor.commit();
                break;

            case "m12":
                editor.putInt("numeroPregunta",13);
                editor.commit();
                break;

            case "m13":
                editor.putInt("numeroPregunta",14);
                editor.commit();
                break;

            case "m14":
                editor.putInt("numeroPregunta",15);
                editor.commit();
                break;

            /*case "m15":
                editor.putInt("numeroPregunta",16);
                editor.commit();
                break;

            case "m16":
                editor.putInt("numeroPregunta",17);
                editor.commit();
                break;

            case "m17":
                editor.putInt("numeroPregunta",18);
                editor.commit();
                break;

            case "m18":
                editor.putInt("numeroPregunta",19);
                editor.commit();
                break;

            case "m19":
                editor.putInt("numeroPregunta",20);
                editor.commit();
                break;*/
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        cargarPreferenciasParticipante();
        //System.out.println("************************************************----------------------------------------------------/////////////////////////////////////Entro en Onresume"+"  "+preferenciasParticipante.getInt("p2",0));
        mapFragment.getMapAsync(this);
        banderaInicio=2;

    }



}
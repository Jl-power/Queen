# Queen

Proyecto Final realizado para la Diplomatura en Android brindada por la UTN

El proyecto consiste en la creacion de una aplicacion desarrollada en Android, la cual requiere aplicar los conocimientos adquiridos en el cursado.

En este caso se elegio desarrollar "Queen", una app que acerca el arte Drag local a aquellas personas que no estan familiarizadas con la movida Drag
pero les interesa tener una plataforma en la cual centralizar estos artistas y poder contactarse de manera mas dinamica.

El arte Drag es una parte importante de la comunidad LGBTIQ+ que ha empezado a crecer mucho y cada vez es mas demandado en los medios de comunicacion,fiestas, discotecas etc, donde antes quizas no habia un espacio de representacion en estos. Es por esta razon nacio Queen como una solucion para agilizar la contratacion laboral como asi tambien poder visibilizar el trabajo y puesta en escenas de estas Reinas.

El proyecto fue llevado a cabo utilizando Kotlin como lenguaje de Programacion, utilizando Android Studio Dolphin 2021.3.1. Se implemento las siguientes librerias:

    //Picasso
    implementation 'com.squareup.picasso:picasso:2.8'
    //Lottie
    implementation "com.airbnb.android:lottie:5.2.0"
    //Firebase
    implementation platform('com.google.firebase:firebase-bom:31.1.0')
    implementation 'com.google.firebase:firebase-firestore-ktx'
    implementation 'com.google.firebase:firebase-auth-ktx'
    //Google Services
    implementation 'com.google.gms:google-services:4.3.13'
    implementation 'com.google.code.gson:gson:2.10'
    
Firebase fue una gran base para el desarrollo de la aplicacion, se utilizo de esta plataforma los servicios de FireStore Database, Authentication y Storage.
Lo cual permitio manejar el sistema de Login, Registro y demas de manera mucho mas rapida, eficiente y simple, como asi tambien almacenar informacion en base de datos No relacional y archivos de imagenes requeridas para el funcionamiento correcto de la aplicacion.

"Queen" tiene dos perfiles de usuarios, los usuarios regulares que ingresan para consultar, contratar o comentar, y los usuarios Drags que necesitan manejar sus perfiles en la app. Esta ultima parte no fue desarrollada por completo debido al escaso tiempo de presentacion del trabajo. Para solucionar esta parte se opto por tener los datos fijos de estos usuarios en los servicios de Firebase.

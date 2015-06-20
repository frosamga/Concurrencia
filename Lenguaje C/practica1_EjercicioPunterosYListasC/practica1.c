/*
 ============================================================================
 Name        : practica1.c
 Author      : dgarrido
 Version     :
 Copyright   : Your copyright notice
 Description : 
 ============================================================================
 */

#include <stdio.h>
#include <stdlib.h>
#include "GestionMemoria.h"

int main () {

	T_manejador manej;
	unsigned ok;
	unsigned dir;

	Crear(&manej);
	Mostrar(manej);

	Obtener(&manej,50,&dir,&ok);
	if (ok) {
		Mostrar(manej);
		printf("la direccion de comienzo es: %d\n", dir);
	} else {
		printf("No es posible obtener esa memoria\n");
	}

	Devolver(&manej, 20,0);
	Mostrar (manej);


	Obtener(&manej,25,&dir,&ok);
	if (ok) {
		Mostrar(manej);
		printf("la direccion de comienzo es: %d\n", dir);
	} else {
		printf("No es posible obtener esa memoria\n");
	}

	Devolver(&manej,10,50);
	Mostrar(manej);

 	Obtener(&manej,25,&dir,&ok);
	if (ok) {
		Mostrar(manej);
		printf("la direccion de comienzo es: %d\n", dir);
	} else {
		printf("No es posible obtener esa memoria\n");
	}

	Devolver(&manej, 20,75);
	Mostrar(manej);


	Destruir(&manej);
	Mostrar (manej);

	printf("Fin Programa\n");

	//system("PAUSE");

	return 0;
}

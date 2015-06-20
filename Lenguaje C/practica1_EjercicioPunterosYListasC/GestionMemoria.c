/*
 * GestionMemoria.c
 *
 *  Created on: 06/03/2012
 *      Author: dgarrido
 */

#include <stdio.h>
#include <stdlib.h>
#include "GestionMemoria.h"

#define MAX 100


/* Crea la estructura utilizada para gestionar la memoria disponible. */
void Crear(T_manejador *Manejador) {
	*Manejador=(T_manejador)malloc(sizeof(struct T_Nodo));
	(*Manejador)->inicio=0;
	(*Manejador)->fin=MAX-1;
	(*Manejador)->sig=NULL;
}

/* Destruye la estructura utilizada. */
void Destruir(T_manejador* manejador) {
	T_manejador ptr;
	while (*manejador != NULL) {
		ptr=(*manejador)->sig;
		free((void *)*manejador);
		*manejador=ptr;
	}
}

/* Devuelve en “dir” la dirección de memoria donde comienza el
 * trozo de memoria continua de tamaño “tam” solicitada.
 * Si la operación se pudo llevar a cabo, es decir, existe dicho
 * trozo, devolvera TRUE en “ok”; FALSE en otro caso.
 */
void Obtener(T_manejador* manejador,unsigned tam, unsigned* dir, unsigned* ok) {
	int tam_actual,restante;
	T_manejador ptr,ant,aux;

	*ok=0;
	ant=NULL;
	ptr=*manejador;

	while ((!(*ok)) && (ptr != NULL)) {
		tam_actual=ptr->fin-ptr->inicio+1;
		if (tam_actual>=tam) {
			*dir=ptr->inicio;
			*ok=1;

			/* Dividir el bloque */
			restante=tam_actual-tam;
			if (restante) {	/* Queda algo de memoria libre, simplemente modificar el contenido */
				ptr->inicio=ptr->inicio+tam;	/* Nueva direccion de inicio */
			}
			else {
				/* Hay que eliminar el nodo de la lista */

				if (ant==NULL) {
					aux=ptr->sig;
					free((void *)*manejador);
					*manejador=aux;
				}
				else {
					/* El bloque esta en medio de la lista */
					ant->sig=ptr->sig;
					free((void *)ptr);
				}

			}	/* end-else dividir bloque */
		}	/* end-if bloque encontrado */
		else {
			ant=ptr;
			ptr=ptr->sig;
		}
	}	/* end-while */

}


/* Compacta la memoria juntando bloques contiguos */
void Compactar(T_manejador *manejador) {
	T_manejador ptr,aux;

	if (*manejador != NULL) {	/* Lista no vacia */

		ptr=*manejador;
		while (ptr->sig != NULL) {
			if (ptr->fin+1==ptr->sig->inicio) {	/* Hay que compactar */
				ptr->fin=ptr->sig->fin;
				aux=ptr->sig->sig;
				free((void *)ptr->sig);
				ptr->sig=aux;
			}
			else {
				ptr=ptr->sig;	/* No hay que compactar, avanzar */
			}

		}
	}

}



/* Devuelve el trozo de memoria continua de tamaño “tam” y que
 * comienza en “dir”.
 * Se puede suponer que se trata de un trozo obtenido previamente.
 */
void Devolver(T_manejador* manejador,unsigned tam,unsigned dir) {
	T_manejador ptr,ant,aux;

	/* Buscar posicion a insertar */
	ptr=*manejador;
	ant=NULL;
	while ((ptr != NULL) && (dir>ptr->inicio)) {
		ant=ptr;
		ptr=ptr->sig;
	}

	if (ant==NULL) {
		/* Insertar al comienzo de la lista */
		aux=(T_manejador)malloc(sizeof(struct T_Nodo));
		aux->inicio=dir;
		aux->fin=dir+tam-1;
		aux->sig=*manejador;
		*manejador=aux;
	} else {
		/* Insertar en medio o al final de la lista */
		aux=(T_manejador)malloc(sizeof(struct T_Nodo));
		aux->inicio=dir;
		aux->fin=dir+tam-1;
		aux->sig=ptr;
		ant->sig=aux;
	}

	/* Hacer compactacion */
	Compactar(manejador);

}



/* Muestra el estado actual de la memoria */
void Mostrar (T_manejador manejador) {
	printf("------\n");
	while (manejador != NULL){
		printf("Desde %d a %d: Libre\n", manejador->inicio, manejador->fin);
		manejador = manejador->sig;
	}

	fflush(stdout);
}

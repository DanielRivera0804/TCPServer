TCPServer
=========
Pasos para la tarea 2

1) Crea en Eclipse un nuevo proyecto llamado Tarea 2, en el pon todos los Javas (de ambos repositores) y los html (existen 2 repositorios distintos solo porque asi lo piden)

2)Conecta 2 PC a la misma red

3)Corre en ambos pc ServidorHTTP.java

4)En 1 de los pc corre TCPServer.java , pero antes debes modificar la linea 13 de TCPClient, reemplazando la IP que se encuentra por la IP del pc donde se correrá el TCPSERVER

5) En ambos computadores, entrar a http://localhost:90/InterfazChat.html

6) Para enviar mensajes entre ellos ingresa el comando: HOLA {ip} {mensaje}
Ejemplo:
HOLA 192.168.0.4 Nachin Te Amo
7) El cambio deberia verse reflejado abajo del chat

8) No se porque, pero el último mensaje enviado jamas se carga completo, pero la transferencia es consistente (un problema con los threads probablemente.


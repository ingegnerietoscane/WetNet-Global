******************************************************************************************************
- Per creare il paccheto di produzione:

mvn -Denv=prod clean install -Dmaven.test.skip=true -o

*E' necessario saltare i test perche' la connessione al db e' quella di produzione


******************************************************************************************************

Per esternalizzare i file di configurazione di WetNet procedere come segue:

-	Per quanto riguarda il server Tomcat, modificare il file TOMCAT_HOME/conf/catalina.properties
	aggiungendo a shared.loader la stringa ${catalina.base}/shared/classes, cioe':
	
	shared.loader=${catalina.base}/shared/classes
	
	Inoltre, se non gia' presente, creare la directory /shared/classes sotto TOMCAT_HOME.
	
	*Se non e' presente la key shared.loader= la stringa di configurazione puo' essere aggiunta
	a common.loader=
	
-	Mettere nella directory TOMCAT_HOME/shared/classes il file di configurazione
	wetnet-configuration.properties con tutte le properties che si vogliono rendere
	configurabili esternamente al .war.
	
-	Riavviare il server Tomcat
	
	
******************************************************************************************************

Per il versionamento di ogni release

modificare tutti e 3 i pom.xml dei vari moduli dell'applicazione WetNet


******************************************************************************************************

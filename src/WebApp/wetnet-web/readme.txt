Per deployare in locale su jetty:
- abilitare le properties in business.properties dentro wetnet-business
- commentare google analytics
- runnare wetnetBuildRun.sh


Per deployare in locale su tomcat:
- disabilitare le properties in business.properties dentro wetnet-business
- commentare google analytics
- runnare wetnetBuild.sh
- copiare war dentro wetnet-web in webapps rinominandolo ROOT


Per produzione:
- aggiornare versioni
- controllare dipendenza versione wetnet-business in wetnet-web
- disabilitare le properties in business.properties dentro wetnet-business
- disabilitare le properties in wetnet-configuration.properties dentro wetnet-business
- togliere commento google analytics in wetnet-google-analytics.js
- runnare wetnetBuild_Produzione.sh
- consegnare war dentro wetnet-web/target rinominandolo con ROOT
- consegnare zip con wetnet-web/wetnet-business




zoom: {
				        enabled: true,
				        rescale: true
				    },
				    subchart: {
				    	  show: true
				    	},
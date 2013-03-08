# GBIFFRANCE-HARVEST

### References
 - https://github.com/michaelakbaraly/gbiffrance-harvest  Code source.
 - http://www.playframework.org/documentation/1.2.3/Home Framework utilisé pour développer l'application

### Configuration de l'utilisation du GBIFFrance-Harvest avec mysql 
 - Créer un utilisateur mysql "moissonnage" avec pour host = ANY (ceci est important car sinon le logiciel refusera de démarrer dans Tomcat)
 - Créer une base de données "tb_moissonnage".
 - Donner tous les droits sauf Grant à l'utilisateur moissonnage sur la base de données tb_moissonnage.
 - Redémarrer le serveur Mysql.
 - Télécharger le fichier "gbiffrance-harvest" dans le dossier /home/tomcat/war
 - Dans le Manager de Tomcat ajouter dans le formulaire les valeurs suivantes : `Chemin de context (requis):  /gbiffrance-harvest ` et `URL vers WAR ou répertoire: /home/tomcat/war/gbiffrance-harvest.war ` puis cliquer sur "Déployer"
 - Créer un dossier : ` mkdir /home/tomcat/moissonnage ` il servira à l'application pour stocker les fichiers de réponses du moissonnage
 - Se rendre dans : ` cd /home/tomcat/webapps/gbiffrance-harvest/WEB-INF/application/conf`
 - Éditer le fichier application.conf : ` vi application.conf `
 - Modifier les paramètres login, password et database de la ligne : ` db=mysql://login:password@localhost:3306/database?autoreconnect=true `
 - Modifier la ligne : ` temp.path=/tmp ` en ` temp.path=/home/tomcat/moissonnage `
 - Dans Tomcat Manager démarrer "/moissonnage".

### Utilisation du GBIFFrance-Harvest 
 - Se rendre sur l'interface
 - Cliquer sur "Add a new DataPublisher" pour ajouter un nouvel organisme
 - Remplissez le formulaire
 - Une fois dans dans l'espace correspondant à l'organisme (le Data Publisher), cliquer sur "Add a new Data Set"
 - Remplissez le formulaire 

### Utiliser Play 1.2.3
 - Télécharger la version 1.2.3 du framework Java Play.
 - Installer le framework dans ` ~/Applications/play/`
 - Se rendre dans le dossier de l'appli.
 - Liste des commandes dispo :
  - Accéder à la liste des commandes : ` ~/Applications/play/play help`
  - Pour actualiser la config de l'application pour Eclipse : ` ~/Applications/play/play eclipsify`
  - Pour lancer l'appli et y avoir accès dans un navigateur : ` ~/Applications/play/play run` puis ouvir un navigateur à l'adresse `http://localhost:9000/ `
  - Pour précompiler l'appli : ` ~/Applications/play/play precompile`
  - Pour générer un fichier war : ` ~/Applications/play/play war -o ~/tmp/gbiffrance-harvest --%production --zip`
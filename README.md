# hakka

Projet d'équipe INF342

## Utiliser Eclipse

Typesafe-activator est une surcouche à pas mal de chose, fournie en plus avec quelques plugins sympas. Néanmoins il reste reste à utiliser un IDE digne de ce nom, par exemple éclipse.

Un script est en cours d'écriture pour automatiser la procédure suivante.

 * Télécharger, installer et ajouter à $PATH `sbt` (on en a besoin pour générer un projet éclipse je crois, en tout cas ça marche avec).
 * Dans le répertoire du projet, exécuter `activator eclipse`, ce qui crée un projet Eclipse.
 * Télécharger, installer et ajouter à $PATH `mvn` (requis).
 * Ajouter [ce plugin](http://eclipse.org/m2e/) à Eclipse pour faire le lien entre ce dernier et maven.
 * Penser à vérifier qu'Eclipse construit bien les index des dépôts et répertoires.
 * Importer le projet dans Eclipse et constater les erreurs de dépendances.
 * Le projet est reconnu comme un projet Java (avec un petit `J` sur l'icône de l'arborescence). Configurer le projet pour le transformer en projet Maven (clic droit sur le projet → Configure → Maven). Un `M` doit alors apparaître à côté du `J`.
 * Puisque __*RIEN*__ sur Stack Overflow ou le site officiel n'explique comment gérer élégament les dépendances, on contourne un peu ça en passant par maven. Editer le fichier de configuration du projet maven (`pom.xml`) en s'inspirant de celui fournit dans `hello-akka-mven`. Cependant, au lieu de faire ça pour chaque projet java, on peut optimier ça en paramétrant correctement `~/.m2/settings.xml`.
 * Rafraîchir le projet Eclipse (clic droit dessus), le projet maven lui-même (clic droit sur le projet → Maven → Update) et enfin reconstruire les index.
 * Les imports sont (dans mon cas) correctement interprétés mais Eclipse peut parfois signaler des exceptions non gérées dans le code par défaut du tutoriel.

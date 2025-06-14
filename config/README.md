Exemple de commande pour avoir un paramétrage externe de la BDD
```
java -jar paymybuddy.jar --spring.config.location=./config/jdbc.properties
```

Avec jdbc.properties contenant les informations de connexion à la BDD. Exemple de contenu :
```
spring.datasource.url=jdbc:mysql://localhost:3306/pay_my_buddy?serverTimezone=UTC
spring.datasource.username=user
spring.datasource.password=mdp
```
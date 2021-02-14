# The Best Midterm Project

Welcome to the best bank application of this world and probably from the whole galaxy!

From now on, you are going to be introduced to the fascinating structure and internal working of this project, so, please, have a nice cup of café con leche in the Plaza Mayor and put your feet up while you read the next Literature Nobel Prize.

## Models

In this part, we are going to focus on the main actors for this application: people...

and money.

Our eleven principal classes are distributed inside the model package (go to: src/main/java/com/ironhack/theBestMidtermProject/model)

User classes are defined inside the package "users":
* User: a regular person. Maybe you, maybe your neighbour, maybe Pepa Pig, but most probably Batman.
* AccountHolder: the best client in the world, since he or she has decided to trust in our bank.
* Admin: may be your mum, because it is the only one putting this mess in order.
* ThirdParty: an external person that knows nothing about life. Poor little thing.
* Role: this class informs about the rights and duties of any user. May you prefer call it Alfred. 

On the other hand, in the package "accounts" we can find:
* Account: I wonder what is this...
* CheckingAccount: the regular one.
* StudentCheckingAccount: the one that you get the first day you go to university.
* SavingsAccount: the one paying that travel to Thailand.
* CreditCardAccount: the one that will ruin you without noticing.

Outside this classification, we can find the class Transactions, which will store every account movement :)

As in real life, every user, account and transaction is unique to us, so a special number will be assigned to each of them. By the way, number five, you owe me money.

Each of these classes has its reflex in the "repository package", which will allow us to store and ask for data from our database.

## Controller

The "controller" package, which you can find in the same directory as model, contains all the functions accessible to the different users. It will answer to your question of: "What can I do?".

It contains two directories: impl with the proper RestControllers, and interfaces (a little child that will yell at you if you do not do what it wants). The impl package contains:

* AccountHolderController: allows creating accountHolders and crying while checking the balance of your accounts.
* AdminController: allows creating admins.
* ThirdPartyController: allows to register a third party in the database. Yes, we are so generous.
* AccountController: allows creating checking and student accounts, and performing transactions.
* CheckingAccountController: allows checking your account balance, and in case you have enough money to pay your apartment's rent, it also allows to subtranct money without your consent. In rare occasions some may also add money. Please, do not freak about this.
* StudentAccountController: same as checking.
* CreditCardController: it is a surprise, go to savingsAccountController to find out.
* SavingsAccountController: as well as crying and ruining yourself as previous controller, this one allows also to create an account of, in this case, savings.

Who would say that interfaces contains the interfaces from these controllers? What a twist in plot!

## Service

The "service" package contains all the logic behind the controllers' method, so it is very alike, but longer and with a lot of restrictions and checks. It contains several levels of security and filters that will not allow thieves and hackers even to breathe. Every class is well commented if you want to know more.

## Security

Lastly but not least, "security" package is our bodyguard, who will tell who can do what, who will encript, desencript and encript again your data, just in case. It contains two classes:

* CustomUserDetails: that will allow you to log in perfectly authenticated.
* SecurityConfiguration: that will allow certain routes to certain people.

## Utils
The utils package includes all the necessary and accessory classes that could not be included inside model, controllers or service. It is divided in three packages:
* classes: contains embeddable classes used by the models: Address, Money and Name. Besides, it includes a Transformer to assemble DTOs and classes with special optional parameters, and a PasswordUtil class if needed.
* dtos: contains the dtos of models and embeddable classes
* enums: contains Salutation and Status, that can only present the values defined inside them.

## Let's get started

First, download Mysql Workbench and Postman and register on both if you do not have done it already. Now, please go to the file "application.properties" in src/main/resources, and change the fields "username" and "password" to your own mysql username and password. This will allow the database to be automatically created. If you do not feel like populating tables from zero, you can use the "superdbQueries.sql" available in the package static inside resources.

Then, open the Intellij terminal and type: mvn spring-boot:run

Let it run, let it run, let it run. Once it stays quiet, open postman and try any of the following routes:

| HTTP verb | Route | Description | Authorization needed | This route is available for the role: |
| --- | --- | --- | --- | --- |
| POST | /register/accountHolder | Register a new account holder | No Auth | all permitted |
| POST | /register/administrator | Register a new administrator | No Auth | all permitted |
| POST | /register/third-party | Register a new third party | Basic Auth | ADMIN |
| POST | /new/checking-account/{userId} | Register a new checking or student account | Basic Auth | ADMIN |
| POST | /new/credit-account/{userId}| Register a new credit card account | Basic Auth | ADMIN |
| POST | /new/savings-account/{userId} | Register a new savings account | Basic Auth | ADMIN |
| POST | /transfer | Perform a transference between account holders | Basic Auth | ACCOUNT_HOLDER |
| POST | /transfer/{hashedKey}/{accountSecretKey} | Perform a transference between an account holder and a third party | Basic Auth | THIRD_PARTY |
| GET | /balance/{accountId} | Check the balance of an account | Basic Auth | ACCOUNT_HOLDER, ADMIN |
| GET | /check/checking-account/{accountId} | Check the information from a checking account | Basic Auth | ACCOUNT_HOLDER, ADMIN |
| GET | /check/credit-card/{accountId} | Check the information from a credit card account | Basic Auth | ACCOUNT_HOLDER, ADMIN |
| GET | /check/savings-account/{accountId} | Check the information from a savings account | Basic Auth | ACCOUNT_HOLDER, ADMIN |
| GET | /check/student-account/{accountId} | Check the information from a student account | Basic Auth | ACCOUNT_HOLDER, ADMIN |
| PATCH | /admin/checking-account/{accountId}/increaseBalance | Add an amount to a checking account's balance | Basic Auth | ADMIN |
| PATCH | /admin/checking-account/{accountId}/decreaseBalance | Subtract an amount from a checking account's balance | Basic Auth | ADMIN |
| PATCH | /admin/credit-card/{accountId}/increaseBalance | Add an amount to a credit card account's balance | Basic Auth | ADMIN |
| PATCH | /admin/credit-card/{accountId}/decreaseBalance | Subtract an amount from a credit card account's balance | Basic Auth | ADMIN |
| PATCH | /admin/savings-account/{accountId}/increaseBalance | Add an amount to a savings account's balance | Basic Auth | ADMIN |
| PATCH | /admin/savings-account/{accountId}/decreaseBalance | Subtract an amount from a savings account's balance | Basic Auth | ADMIN |
| PATCH | /admin/student-account/{accountId}/increaseBalance | Add an amount to a student account's balance | Basic Auth | ADMIN |
| PATCH | /admin/student-account/{accountId}/decreaseBalance | Subtract an amount from a student account's balance | Basic Auth | ADMIN |

**Do not forget to add "localhost:8080" before each route.**

## TDD

All the testing is inside the path src/test/java. Since the controllers call the services, and those are the ones responsible for the logic of the program, the testing of these controllers would allow to check the correct behaviour of both controllers and services. Therefore, the available tests are inside classes:

* AccountControllerTest
* AccountHolderControllerTest
* AdminControllerTest
* CheckingAccountControllerTest
* CreditCardControllerTest
* SavingsAccountControllerTest
* StudentAccountControllerTest
* ThirdPartyControllerTest

## All yours

Any feedback for improvement is welcome. Meanwhile, I hope you have fun with this project, but remember that life is too short to waste it with testing.
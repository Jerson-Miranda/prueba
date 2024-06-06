CREATE DATABASE db_diet;

USE db_diet;

CREATE TABLE category (
id_category BIGINT PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(50) NOT NULL,
owner VARCHAR(15) NOT NULL
);

CREATE TABLE type_ingredient (
id_type_ingredient BIGINT PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(50) NOT NULL
);

CREATE TABLE nutricional_fact (
id_nutricional_fact BIGINT PRIMARY KEY AUTO_INCREMENT,
kcal DECIMAL(10,2) NOT NULL,
protein DECIMAL(10,2) NOT NULL,
carbohydrate DECIMAL(10,2) NOT NULL,
sugar DECIMAL(10,2) NOT NULL,
added_sugar DECIMAL(10,2) NOT NULL,
fat DECIMAL(10,2) NOT NULL,
saturated_fat DECIMAL(10,2) NOT NULL,
fiber DECIMAL(10,2) NOT NULL,
sodium DECIMAL(10,2) NOT NULL
);

CREATE TABLE ingredient (
id_ingredient BIGINT PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(50) NOT NULL,
brand VARCHAR(50) NOT NULL,
description VARCHAR(100) NOT NULL,
price DECIMAL(10,2) NOT NULL,
gr_ml_pza DECIMAL(10,2) NOT NULL,
category BIGINT NOT NULL,
type BIGINT NOT NULL,
nutricional_fact BIGINT NOT NULL,
FOREIGN KEY (category) REFERENCES category(id_category),
FOREIGN KEY (type) REFERENCES type_ingredient(id_type_ingredient),
FOREIGN KEY (nutricional_fact) REFERENCES nutricional_fact(id_nutricional_fact)
);

CREATE TABLE recipe_book (
id_recipe_book BIGINT PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(50) NOT NULL,
time_minute INT NOT NULL,
procedure_ LONGTEXT NOT NULL,
photo VARCHAR(255) NOT NULL,
total_kcal DECIMAL(10,2) NOT NULL,
total_protein DECIMAL(10,2) NOT NULL,
total_carbohydrate DECIMAL(10,2) NOT NULL,
total_sugar DECIMAL(10,2) NOT NULL,
total_added_sugar DECIMAL(10,2) NOT NULL,
total_fat DECIMAL(10,2) NOT NULL,
total_saturated_fat DECIMAL(10,2) NOT NULL,
total_fiber DECIMAL(10,2) NOT NULL,
total_sodium DECIMAL(10,2) NOT NULL,
category BIGINT NOT NULL,
FOREIGN KEY (category) REFERENCES category(id_category)
);

CREATE TABLE diet (
id_diet BIGINT PRIMARY KEY AUTO_INCREMENT,
date DATE NOT NULL,
total_kcal DECIMAL(10,2) NOT NULL,
total_protein DECIMAL(10,2) NOT NULL,
total_carbohydrate DECIMAL(10,2) NOT NULL,
total_sugar DECIMAL(10,2) NOT NULL,
total_added_sugar DECIMAL(10,2) NOT NULL,
total_fat DECIMAL(10,2) NOT NULL,
total_saturated_fat DECIMAL(10,2) NOT NULL,
total_fiber DECIMAL(10,2) NOT NULL,
total_sodium DECIMAL(10,2) NOT NULL,
time_minute INT NOT NULL
);

CREATE TABLE ingredient_recipe_book (
id_ingredient BIGINT NOT NULL,
id_recipe_book BIGINT NOT NULL,
amount INT NOT NULL,
PRIMARY KEY (id_ingredient, id_recipe_book),
FOREIGN KEY (id_ingredient) REFERENCES ingredient(id_ingredient),
FOREIGN KEY (id_recipe_book) REFERENCES recipe_book(id_recipe_book)
);

CREATE TABLE diet_recipe_book (
id_diet BIGINT NOT NULL,
id_recipe_book BIGINT NOT NULL,
portion INT NOT NULL,
PRIMARY KEY (id_diet, id_recipe_book),
FOREIGN KEY (id_diet) REFERENCES diet(id_diet),
FOREIGN KEY (id_recipe_book) REFERENCES recipe_book(id_recipe_book)
);

CREATE TABLE pantry (
id_pantry BIGINT PRIMARY KEY AUTO_INCREMENT,
ingredient BIGINT NOT NULL,
expiration_date DATE NOT NULL,
stock INT NOT NULL,
FOREIGN KEY (ingredient) REFERENCES ingredient(id_ingredient)
);
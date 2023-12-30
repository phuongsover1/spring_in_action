CREATE TABLE IF NOT EXISTS Taco_Order (
    id identity PRIMARY KEY,
    delivery_Name varchar(50) not null,
    delivery_Street varchar(50) not null,
    delivery_City varchar(50) not null,
    delivery_State varchar (2) not null,
    delivery_Zip varchar(10) not null,
    cc_number varchar(16) not null,
    cc_expiration varchar(5) not null,
    cc_cvv varchar(3) not null,
    placed_at timestamp not null
);

CREATE TABLE IF NOT EXISTS Taco(
    id identity,
    name varchar(50) not null,
    taco_order bigint not null,
    taco_order_key bigint not null,
    created_at timestamp not null
);

CREATE TABLE IF NOT EXISTS Ingredient_Ref (
    ingredient VARCHAR(4) NOT NULL,
    taco BIGINT not NULL,
    taco_key BIGINT not NULL
);

create TABLE if NOT EXISTS Ingredient (
    id VARCHAR(4) PRIMARY KEY not null,
    name VARCHAR(25) not NULL,
    type VARCHAR(10) not NULL
);

ALTER TABLE Taco 
    add foreign key (taco_order) references Taco_Order(id);
ALTER TABLE Ingredient_Ref
    add foreign key (ingredient) references Ingredient(id);
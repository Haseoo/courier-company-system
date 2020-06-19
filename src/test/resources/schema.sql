CREATE TABLE IF NOT EXISTS address
(
    id             bigserial not null,
    buildingnumber text      not null,
    city           text      not null,
    flatnumber     text,
    postalcode     text      not null,
    street         text      not null,
    constraint address_pkey
        primary key (id)
);

CREATE TABLE IF NOT EXISTS companyuser
(
    id       bigserial not null,
    active   boolean   not null,
    password varchar(255),
    username varchar(255),
    constraint companyuser_pkey
        primary key (id)
);

CREATE TABLE IF NOT EXISTS client
(
    clienttype   integer      not null,
    emailaddress varchar(255) not null,
    phonenumber  varchar(255),
    userid       bigint       not null,
    constraint client_pkey
        primary key (userid),
    constraint fkll66vn5uvokbyub73mgsol71k
        foreign key (userid) references companyuser
);

CREATE TABLE IF NOT EXISTS clientcompany
(
    companyname                text         not null,
    nip                        varchar(255) not null,
    representativeemailaddress varchar(255) not null,
    representativename         text         not null,
    representativephonenumber  varchar(255) not null,
    representativesurname      text         not null,
    clientid                   bigint       not null,
    constraint clientcompany_pkey
        primary key (clientid),
    constraint fktfw64xdm0rb6v16mj5h70nmpn
        foreign key (clientid) references client
);

CREATE TABLE IF NOT EXISTS clientindividual
(
    imageurl varchar(255),
    name     text   not null,
    pesel    varchar(255),
    surname  text,
    clientid bigint not null,
    constraint clientindividual_pkey
        primary key (clientid),
    constraint fkap2vx925ke9024raswh4lpuul
        foreign key (clientid) references client
);

CREATE TABLE IF NOT EXISTS employee
(
    name        text         not null,
    pesel       varchar(255) not null,
    phonenumber varchar(255) not null,
    surname     text         not null,
    userid      bigint       not null,
    constraint employee_pkey
        primary key (userid),
    constraint fk1tu9g0cbt6hghhc3xc05fmsd
        foreign key (userid) references companyuser
);

CREATE TABLE IF NOT EXISTS courier
(
    employeeid bigint not null,
    constraint courier_pkey
        primary key (employeeid),
    constraint fkh5q2xjkfwm0qxb5px1w740tp2
        foreign key (employeeid) references employee
);

CREATE TABLE IF NOT EXISTS magazine
(
    id         bigserial not null,
    active     boolean   not null,
    address_id bigint,
    constraint magazine_pkey
        primary key (id),
    constraint fk5pm3l5ckcr7f9ovnneyi8cgev
        foreign key (address_id) references address
);

CREATE TABLE IF NOT EXISTS logistician
(
    employeeid bigint not null,
    magazineid bigint,
    constraint logistician_pkey
        primary key (employeeid),
    constraint fkfwakq6qs5hlmx3a3m79fj9m2e
        foreign key (magazineid) references magazine,
    constraint fkc6vnk5tk0r4gta37q8wgo2c0f
        foreign key (employeeid) references employee
);

CREATE TABLE IF NOT EXISTS parceltype
(
    id          bigserial      not null,
    isactive    boolean        not null,
    description text           not null,
    name        varchar(255)   not null,
    price       numeric(19, 2) not null,
    constraint parceltype_pkey
        primary key (id)
);

CREATE TABLE IF NOT EXISTS receiverinfo
(
    id           bigserial    not null,
    emailaddress varchar(255),
    name         text         not null,
    phonenumber  varchar(255) not null,
    surname      text         not null,
    constraint receiverinfo_pkey
        primary key (id)
);

CREATE TABLE IF NOT EXISTS parcel
(
    id                         bigserial      not null,
    isdatemoved                boolean        not null,
    expectedcourierarrivaldate date,
    ispaid                     boolean        not null,
    parcelfee                  numeric(19, 2) not null,
    pin                        varchar(255)   not null,
    priority                   boolean        not null,
    istoreturn                 boolean        not null,
    deliveryaddressid          bigint         not null,
    parceltypeid               bigint         not null,
    receivercontactdataid      bigint         not null,
    clientid                   bigint,
    senderaddressid            bigint         not null,
    constraint parcel_pkey
        primary key (id),
    constraint fkpulpqy4m37ta35jbem53eoj2s
        foreign key (deliveryaddressid) references address,
    constraint fk295tkwq3qefg88w9gibk9n7tl
        foreign key (parceltypeid) references parceltype,
    constraint fkhgx2k9yxknm0aci33mynql7en
        foreign key (receivercontactdataid) references receiverinfo,
    constraint fk7xl5l9ku7dv4eoqxb6jsv96aa
        foreign key (clientid) references client,
    constraint fkcfxqqdvueg3q4648vq29styyc
        foreign key (senderaddressid) references address
);

CREATE TABLE IF NOT EXISTS client_parcel
(
    clientid bigint not null,
    parcelid bigint not null,
    constraint fkbwxu44ylqfw72lygr69vnsnpf
        foreign key (parcelid) references parcel,
    constraint fkouqrteleqfikuvlj2vefqc4ky
        foreign key (clientid) references client
);

CREATE TABLE IF NOT EXISTS parcelstaterecord
(
    id         bigserial not null,
    changedate timestamp not null,
    state      integer   not null,
    courierid  bigint,
    magazineid bigint,
    parcelid   bigint    not null,
    constraint parcelstaterecord_pkey
        primary key (id),
    constraint fk6jqqh525uurbdkh4h84p7xccl
        foreign key (courierid) references courier,
    constraint fkhjm85mgrienyw7gbqb974ke6s
        foreign key (magazineid) references magazine,
    constraint fk26ntyol5kttkj0kwwug2sgwv3
        foreign key (parcelid) references parcel
);

CREATE TABLE IF NOT EXISTS test (
    testRow bigint
);

 

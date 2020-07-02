create table address
(
    id             bigserial not null
        constraint address_pkey
            primary key,
    buildingnumber text      not null,
    city           text      not null,
    flatnumber     text,
    postalcode     text      not null,
    street         text      not null
);

create table companyuser
(
    id       bigserial not null
        constraint companyuser_pkey
            primary key,
    active   boolean   not null,
    password varchar(255),
    username varchar(255)
);

create table client
(
    clienttype   integer      not null,
    emailaddress varchar(255) not null,
    phonenumber  varchar(255),
    userid       bigint       not null
        constraint client_pkey
            primary key
        constraint fkll66vn5uvokbyub73mgsol71k
            references companyuser
);

create table clientcompany
(
    companyname                text         not null,
    nip                        varchar(255) not null,
    representativeemailaddress varchar(255) not null,
    representativename         text         not null,
    representativephonenumber  varchar(255) not null,
    representativesurname      text         not null,
    clientid                   bigint       not null
        constraint clientcompany_pkey
            primary key
        constraint fktfw64xdm0rb6v16mj5h70nmpn
            references client
);

create table clientindividual
(
    imageurl varchar(255),
    name     text   not null,
    pesel    varchar(255),
    surname  text,
    clientid bigint not null
        constraint clientindividual_pkey
            primary key
        constraint fkap2vx925ke9024raswh4lpuul
            references client
);

create table employee
(
    name        text         not null,
    pesel       varchar(255) not null,
    phonenumber varchar(255) not null,
    surname     text         not null,
    userid      bigint       not null
        constraint employee_pkey
            primary key
        constraint fk1tu9g0cbt6hghhc3xc05fmsd
            references companyuser
);

create table courier
(
    employeeid bigint not null
        constraint courier_pkey
            primary key
        constraint fkh5q2xjkfwm0qxb5px1w740tp2
            references employee
);

create table magazine
(
    id         bigserial not null
        constraint magazine_pkey
            primary key,
    active     boolean   not null,
    address_id bigint
        constraint fk5pm3l5ckcr7f9ovnneyi8cgev
            references address
);

create table logistician
(
    employeeid bigint not null
        constraint logistician_pkey
            primary key
        constraint fkc6vnk5tk0r4gta37q8wgo2c0f
            references employee,
    magazineid bigint
        constraint fkfwakq6qs5hlmx3a3m79fj9m2e
            references magazine
);

create table parceltype
(
    id          bigserial      not null
        constraint parceltype_pkey
            primary key,
    isactive    boolean        not null,
    description text           not null,
    name        varchar(255)   not null,
    price       numeric(19, 2) not null
);

create table receiverinfo
(
    id           bigserial    not null
        constraint receiverinfo_pkey
            primary key,
    emailaddress varchar(255),
    name         text         not null,
    phonenumber  varchar(255) not null,
    surname      text         not null
);

create table parcel
(
    id                         bigserial      not null
        constraint parcel_pkey
            primary key,
    isdatemoved                boolean        not null,
    expectedcourierarrivaldate date,
    ispaid                     boolean        not null,
    parcelfee                  numeric(19, 2) not null,
    pin                        varchar(255)   not null,
    priority                   boolean        not null,
    istoreturn                 boolean        not null,
    deliveryaddressid          bigint         not null
        constraint fkpulpqy4m37ta35jbem53eoj2s
            references address,
    parceltypeid               bigint         not null
        constraint fk295tkwq3qefg88w9gibk9n7tl
            references parceltype,
    receivercontactdataid      bigint         not null
        constraint fkhgx2k9yxknm0aci33mynql7en
            references receiverinfo,
    clientid                   bigint
        constraint fk7xl5l9ku7dv4eoqxb6jsv96aa
            references client,
    senderaddressid            bigint         not null
        constraint fkcfxqqdvueg3q4648vq29styyc
            references address
);

create table client_parcel
(
    clientid bigint not null
        constraint fkouqrteleqfikuvlj2vefqc4ky
            references client,
    parcelid bigint not null
        constraint fkbwxu44ylqfw72lygr69vnsnpf
            references parcel
);

create table parcelstaterecord
(
    id         bigserial not null
        constraint parcelstaterecord_pkey
            primary key,
    changedate timestamp not null,
    state      integer   not null,
    courierid  bigint
        constraint fk6jqqh525uurbdkh4h84p7xccl
            references courier,
    magazineid bigint
        constraint fkhjm85mgrienyw7gbqb974ke6s
            references magazine,
    parcelid   bigint    not null
        constraint fk26ntyol5kttkj0kwwug2sgwv3
            references parcel
);

create table estimateddeliverytime
(
    expectedcourierarrivalafteraddtomagazine integer,
    expectedcourierarrivalafteradd           integer,
    timesatmagazinetoreturn                  integer,
    maxmovedayafter                          integer,
    id                                       integer
);

INSERT INTO public.companyuser (id, active, password, username) VALUES (100, true, '$2a$12$QOpC9AXqeKDBcNMWHFr/oOEjcJqxD1IkbZsvshHsTRvsIsLpVbji6', 'admin');
INSERT INTO public.estimateddeliverytime (expectedcourierarrivalafteraddtomagazine, expectedcourierarrivalafteradd, timesatmagazinetoreturn, maxmovedayafter, id) VALUES (1, 2, 2, 4, 1);

 

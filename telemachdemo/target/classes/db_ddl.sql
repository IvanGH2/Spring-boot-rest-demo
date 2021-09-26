CREATE DATABASE telemach_db
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Croatian_Croatia.1250'
    LC_CTYPE = 'Croatian_Croatia.1250'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;
    

CREATE TABLE public.address
(
    id integer NOT NULL,
    street_no character varying(16) COLLATE pg_catalog."default" NOT NULL,
    street character varying(128) COLLATE pg_catalog."default" NOT NULL,
    city character varying(128) COLLATE pg_catalog."default" NOT NULL,
    post character varying(128) COLLATE pg_catalog."default" NOT NULL,
    post_no integer NOT NULL,
    CONSTRAINT address_pkey PRIMARY KEY (id)
)

CREATE TABLE public.service
(
    id integer NOT NULL DEFAULT nextval('service_id_seq'::regclass),
    address_id integer NOT NULL,
    service character varying(32) COLLATE pg_catalog."default",
    value boolean NOT NULL,
    comment character varying(256) COLLATE pg_catalog."default",
    CONSTRAINT service_pkey PRIMARY KEY (id),
    CONSTRAINT address_id_fk FOREIGN KEY (address_id)
        REFERENCES public.address (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
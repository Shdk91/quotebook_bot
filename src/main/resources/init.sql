create table user(
   id bigint not null,
   name varchar not null,
   isAdmin bool default false,
   primary key (id)
);

create table quote(
    id varchar not null,
    text varchar not null,
    author varchar not null,
    genre varchar not null,
    v bigint,
    primary key (id)
);

create table user_quote(
    user_id bigint not null,
    quote_id varchar not null,
    primary key (user_id, quote_id),
    foreign key (user_id) references user(id),
    foreign key (quote_id) references quote(id)
);


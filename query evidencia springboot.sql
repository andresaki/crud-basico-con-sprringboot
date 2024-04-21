
-- querys para crea la base de datos , la tabla y algunos registros

create database inventarioSpringBoot;
use inventarioSpringBoot;

insert into producto (nombre, categoria, unidades_actuales , stock_minimo, precio_unitario , fecha_creacion ) 
values
  ('Laptop Gamer', 'Computadoras', 10, 5, 850000 , current_date()),
  ('PC de escritorio', 'Computadoras', 8, 4, 1200000 , current_date()),

  ('Smartphone X', 'Telefonía', 15, 8, 325000 , current_date()),
  ('Tablet 10"', 'Telefonía', 6, 3, 250000 , current_date()),

  ('Audífonos inalámbricos', 'Audio', 7, 3, 120000 , current_date()),
  ('Parlantes Bluetooth', 'Audio', 5, 2, 80000 , current_date()),

  ('Impresora multifunción', 'Oficina', 3, 2, 475000 , current_date()),
  ('Monitor LCD', 'Oficina', 12, 5, 350000 , current_date()),

  ('Smart TV 55"', 'TV y Video', 8, 4, 1300000 , current_date()),
  ('Proyector Full HD', 'TV y Video', 2, 1, 750000 , current_date());





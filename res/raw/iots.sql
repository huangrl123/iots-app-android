--感知端信息表
create table t_perception(
	perception_type_id integer not null,
	perception_type_name text not null,
	perception_id  integer not null,
	perception_name integer not null,
	perception_addr text not null,
	install_site integer not null,
	is_on_line boolean not null,
	last_comm_time text not null
);

--感知端运行日志表
create table t_perception_runtime_log(
	perception_runtime_log_id integer not null,
	perception_id integer not null,
	perception_name text not null,
	perception_addr text not null,
	perception_param_id integer not null,
	perception_param_name text not null,
	perception_type_id integer not null,
	perception_type_name text not null,
	perception_param_value_info_id integer not null,
	perception_param_value_desc text not null,
	create_date_time text not null,
	remark text
);
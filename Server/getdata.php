<?php
$data = array(
	array(
	"name"=>"Lee",
	"game"=>"Running",
	"description"=>"Running from rest, work out",
	"location" =>array(108.119,108.119)
	),
	array(
	"name"=>"Vu",
	"game"=>"Jumping",
	"description"=>"All jump, work out",
	"location" =>array(108.119,108.119)
	),
	array(
	"name"=>"XG",
	"game"=>"Fishing",
	"description"=>"fishing is fun",
	"location" =>array(108.119,108.119)
	),
);
header('Content-Type: application/json');
echo json_encode($data);
?>

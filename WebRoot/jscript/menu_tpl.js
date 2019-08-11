/*
  --- menu level scope settins structure --- 
 
*/


var MENU_POS = [{
	// item sizes
	'height': 24,
	'width': 130,
	// menu block offset from the origin:
	//	for root level origin is upper left corner of the page
	//	for other levels origin is upper left corner of parent item
	'block_top': 165,		
	'block_left': 25,		
	// offsets between items of the same level
	'top': 0,
	'left': 131,
	// time in milliseconds before menu is hidden after cursor has gone out
	// of any items
	'hide_delay': 200,
	'expd_delay': 200,
	'css' : {
		'outer' : ['m0l0oout', 'm0l0oover'],
		'inner' : ['m0l0iout', 'm0l0iover']
	}
},{
	'height': 24,  //24
	'width': 185,
	'block_top': 25,  //25
	'block_left': 0,
	'top': 23,
	'left': 0,
	'css' : {
		'outer' : ['m0l1oout', 'm0l1oover'],
		'inner' : ['m0l1iout', 'm0l1iover']
	}
},{
	'block_top': 5,
	'block_left': 160
}
];

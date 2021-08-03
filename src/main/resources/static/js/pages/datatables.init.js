$(document).ready(function(){
	$("#datatable").DataTable({
		drawCallback:function(){
			$(".dataTables_paginate > .pagination").addClass("pagination-rounded")
		},
	});
	var a=$("#datatable-buttons").DataTable({
		select:{
			style:"multi"
		},
		scrollY:"400px",
		scrollCollapse:!0,
		lengthChange:!1,
		//pagingType:"full_numbers",
		drawCallback:function(){
			$(".dataTables_paginate > .pagination").addClass("pagination-rounded")
		},
		paginate: false,
		buttons:["copy","excel","csv","colvis"]
	});
	a.buttons().container().appendTo("#datatable-buttons_wrapper .col-md-6:eq(0)");
	$("#selection-datatable").DataTable({
		select:{
			style:"multi"
		},
		language:{
			paginate:{
				previous:"<i class='mdi mdi-chevron-left'>",
				next:"<i class='mdi mdi-chevron-right'>"
			}
		},
		drawCallback:function(){
			$(".dataTables_paginate > .pagination").addClass("pagination-rounded")
		}
	}),
	$("#key-datatable").DataTable({
		keys:!0,
		pagingType:"full_numbers",
		drawCallback:function(){
			$(".dataTables_paginate > .pagination").addClass("pagination-rounded")
		}
	}),
	a.buttons().container().appendTo("#datatable-buttons_wrapper .col-md-6:eq(0)"),
	$("#alternative-page-datatable").DataTable({
		pagingType:"full_numbers",
		drawCallback:function(){
			$(".dataTables_paginate > .pagination").addClass("pagination-rounded")
		}
	}),
	$("#scroll-vertical-datatable").DataTable({
		scrollY:"350px",
		scrollCollapse:!0,
		paging:!1,
		language:{
			paginate:{
				previous:"<i class='mdi mdi-chevron-left'>",
				next:"<i class='mdi mdi-chevron-right'>"
			}
		},
		drawCallback:function(){
			$(".dataTables_paginate > .pagination").addClass("pagination-rounded")
		}
	}),
	$("#complex-header-datatable").DataTable({
		language:{
			paginate:{
				previous:"<i class='mdi mdi-chevron-left'>",
				next:"<i class='mdi mdi-chevron-right'>"
			}
		},
		drawCallback:function(){
			$(".dataTables_paginate > .pagination").addClass("pagination-rounded")
		},
		columnDefs:[{visible:!1,targets:-1}]}),
	$("#state-saving-datatable").DataTable({
		stateSave:!0,
		language:{
			paginate:{
				previous:"<i class='mdi mdi-chevron-left'>",
				next:"<i class='mdi mdi-chevron-right'>"
			}
		},
		drawCallback:function(){
			$(".dataTables_paginate > .pagination").addClass("pagination-rounded")
		}
	})
});
/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

 
$(document).ready(function() {
 

	if (!window['console'])
	{
		window.console = {};
		window.console.log = function(){};
	}
		
	/*
	define a new language named "custom"
	*/

	$.dateRangePickerLanguages['custom'] = 
	{
		'selected': 'Выбрано:',
		'days': 'Дн.',
		'apply': 'Закрыть',
		'week-1' : 'Пн',
		'week-2' : 'Вт',
		'week-3' : 'Ср',
		'week-4' : 'Чт',
		'week-5' : 'Пт',
		'week-6' : 'Сб',
		'week-7' : 'Вс',
		'month-name': ['Январь','Февараль','Март','Апрель','Май','Июнь','Июль','Август','Сентябрь','Октбярь','Ноябрь','Декабрь'],
		'shortcuts' : 'Ссылки',
		'past': 'Прошл.',
		'7days' : '7дней',
		'14days' : '14дней',
		'30days' : '30дней',
		'previous' : 'Назад',
		'prev-week' : 'Week',
		'prev-month' : 'Месяц',
		'prev-quarter' : 'Квартал',
		'prev-year' : 'Год',
		'less-than' : 'Date range should longer than %d days',
		'more-than' : 'Date range should less than %d days',
		'default-more' : 'Please select a date range longer than %d days',
		'default-less' : 'Please select a date range less than %d days',
		'default-range' : 'Please select a date range between %d and %d days',
		'default-default': 'Выберите период'
	};
	
  
        
	$('#date-range0').dateRangePicker(
	{inline:true,
        container: '#date-range0-container',
        format: 'DD.MM.YYYY',
        separator: ' по ',
	alwaysOpen:true,
        selectForward: true,
        startOfWeek: 'monday',
        language: 'custom' 
        ,getValue: function()
		{
			if ($('#datepicker_dBegin').val() && $('#datepicker_dEnd').val() )
				return $('#datepicker_dBegin').val() + ' по ' + $('#datepicker_dEnd').val();
			else
				return '';
		}
        ,
		setValue: function(s,s1,s2)
		{
			$('#datepicker_dBegin').val(s1);
			$('#datepicker_dEnd').val(s2);
		}
                     
	,beforeShowDay: function(t)
	{
                
		var valid =  !( $('#datepicker_dBegin').prop('disabled') );  //disable everything
		var _class = '';
		var _tooltip = valid ? '' :   'disabled';
		return [valid,_class,_tooltip];
	}
	}).bind('datepicker-first-date-selected', function(event, obj)
	{
		/* This event will be triggered when first date is selected */
		console.log('first-date-selected',obj);
		// obj will be something like this:
		// {
		// 		date1: (Date object of the earlier date)
		// }
	})
	.bind('datepicker-change',function(event,obj)
	{
		/* This event will be triggered when second date is selected */
		console.log('change',obj);
		// obj will be something like this:
		// {
		// 		date1: (Date object of the earlier date),
		// 		date2: (Date object of the later date),
		//	 	value: "2013-06-05 to 2013-06-07"
		// }
	})
	.bind('datepicker-apply',function(event,obj)
	{
		/* This event will be triggered when user clicks on the apply button */
		console.log('apply',obj);
	})
	.bind('datepicker-close',function()
	{
		/* This event will be triggered before date range picker close animation */
		console.log('before close');
	})
	.bind('datepicker-closed',function()
	{
		/* This event will be triggered after date range picker close animation */
		console.log('after close');
	})
	.bind('datepicker-open',function()
	{
		/* This event will be triggered before date range picker open animation */
		console.log('before open');
	})
	.bind('datepicker-opened',function()
	{
		/* This event will be triggered after date range picker open animation */
		console.log('after open');
	});
 
});   
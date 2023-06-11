/**
 *  Plug-in offers the same functionality as `full_numbers` pagination type 
 *  (see `pagingType` option) but without ellipses.
 *
 *  See [example](http://www.gyrocode.com/articles/jquery-datatables-pagination-without-ellipses) for demonstration.
 *
 *  @name Full Numbers - No Ellipses
 *  @summary Same pagination as 'full_numbers' but without ellipses
 *  @author [Michael Ryvkin](http://www.gyrocode.com)
 *
 *  @example
 *    $(document).ready(function() {
 *        $('#example').dataTable( {
 *            "pagingType": "full_numbers_no_ellipses"
 *        } );
 *    } );
 */

$.fn.DataTable.ext.pager.full_numbers_no_ellipses = function(page, pages){
   let numbers = [];
   let buttons = $.fn.DataTable.ext.pager.numbers_length;
   let half = Math.floor( buttons / 2 );

   let _range = function ( len, start ){
      let end;
   
      if ( typeof start === "undefined" ){ 
         start = 0;
         end = len;

      } else {
         end = start;
         start = len;
      }

      let out = [];
      for ( let i = start ; i < end; i++ ){ out.push(i); }
   
      return out;
   };
    

   if ( pages <= buttons ) {
      numbers = _range( 0, pages );

   } else if ( page <= half ) {
      numbers = _range( 0, buttons);

   } else if ( page >= pages - 1 - half ) {
      numbers = _range( pages - buttons, pages );

   } else {
      numbers = _range( page - half, page + half + 1);
   }

   numbers.DT_el = 'span';

   return [ 'first', 'previous', numbers, 'next', 'last' ];
};

$.dt_adjust_pager_number = function(dtable){
   let ipag = $.fn.DataTable.ext.pager.numbers_length,i,
       w = document.documentElement.clientWidth;
   if (w < 768) {
      i = 3;
   } else if (w <= 1024) {
      i = 5;
   } else if (w > 1024) {
      i = 7;
   }

   if (i && ipag != i ){
      $.fn.DataTable.ext.pager.numbers_length = i;
      if (typeof dtable == 'object'){
         try {
            dtable.draw('page');
         } catch (e) {
            location.reload();
         }
      }
   }
};

$.dt_adjust_pager_number();

let osTable = $.fn.dataTable.tables(); // $.fn.dataTable.tables();

$(window).resize(function () {
   if (window.tmdoit){
      window.clearTimeout(window.tmdoit);
   }
   window.tmdoit = window.setTimeout(function(){
      $.dt_adjust_pager_number(osTable);
   },400);
});

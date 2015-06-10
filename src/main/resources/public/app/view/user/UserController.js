Ext.define('TrabalhoPraticoEsApp.view.user.UserController', {
    extend: 'Ext.app.ViewController',  
    alias: 'controller.user',

    onClickCarregar: function(btn, e, eOpts) {
    	var store = btn.up('grid').getStore('clienteStore');
    	store.load({params:{start:0, limit:10}});
	},

	onClickLimpar: function(btn, e, eOpts) {
		btn.up('grid').getStore('clienteStore').loadData([],false);
	},

	onDblClick: function(grid, record, item, index, e, eOpts) {

		var m = Ext.create('AppCliente.view.cliente.ClienteEdit');
		grid.panel.add(m);
		var f = m.down('form');
		f.loadRecord(record);
		console.log(record);
		
	},

	onClickExcluir: function(btn, e, eOpts){

		var grid = btn.up('grid');
		var rec = grid.getSelectionModel().getSelection()[0];
		
		var vm = this.getViewModel();
		var store = vm.getStore('clienteStore');
		
		store.getProxy().setExtraParams({id: rec.id});
		//console.log(rec);		
		store.remove(rec);
	},
	
	onClickNovo:function(btn, e, eOpts){
		var m = Ext.create('AppCliente.view.cliente.ClienteEdit');
	},
	
	onClickLimpaTela: function(btn, e, eOpts){
		var w = btn.up('window');
		var f = w.down('form');

		f.getForm().reset();

		w.close();
	},

	onClickSalvar: function(btn, e, eOpts){
		
		var vm = this.getViewModel();
		var store = vm.getStore('clienteStore');
		var window = btn.up('window');
		var form = window.down('form');
		var record = form.getRecord();
		
		if (record){
			var data = record.get('dataNascimento');
			record.set('dataNascimento', Ext.Date.format(new Date(data),'d/m/Y H:i'));
			
			record.save({
	            success: function () {
	                Ext.Msg.alert('SALVOU', 'aeeee');
	            },
	            failure: function() {
	            	Ext.Msg.alert('ERRO','Deu Pau');
	            }
			});
			
		}else{
			var record = Ext.create('AppCliente.view.cliente.ClienteModel');
			var values = form.getValues();
			record.set(values);
			var data = record.get('dataNascimento');
			record.set('dataNascimento', Ext.Date.format(new Date(data),'d/m/Y'));
			store.insert(0,record);
		};
		
	}


});

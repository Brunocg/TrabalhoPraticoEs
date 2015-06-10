Ext.define('TrabalhoPraticoEsApp.model.user.UserModel',{
    extend: 'Ext.app.ViewModel',
    alias: 'model.user',  

	proxy:{
		url : '/user/',
		timeout: 50000,
        api: {
            create:  '/user/novo',
            read:    '/user/listar',
            update:  '/user/atualizar',
            destroy: '/user/excluir'
        },
		type: 'ajax',
		reader: {
			type:'json',
			rootProperty: 'content'
		},
        writer: {
            type: 'json'
        }
	}

    
//    statics: {
//    	excluirCliente:function(clienteId,success,failure,callback,scope){
//    		Ext.Ajax.request({
//    			url:'cliente/excluir',
//    			method:'GET',
//    			params:{'clienteId':clienteId},
//    			success:success,
//    			failure:failure,
//    			callback:callback,
//    			scope:scope
//    		});
//    	}
//    }
});
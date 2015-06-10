Ext.define('TrabalhoPraticoEsApp.view.user.UserViewModel',{
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.user',  

    stores:{
    	userStore:{
    		fields: ['id', 'nome', 'rua', 'numero', 'bairro', 'cidade', 'cep', 'uf', {name: 'dataNascimento', type: 'date', dateFormat: 'd/m/Y'},
    				 'cpf', 'inscricaoRg', {name:'ddd', type: 'int'} , {name:'telefone', type: 'int'}, 'email', 'sexo', 'observacao' ],
    		autoLoad: true,
    		autoSync: true,
    		pageSize: 0, 
    		sortParam : undefined,
            startParam : undefined,
            pageParam : undefined,
            limitParam : undefined,
            noCache : false,
    		params: {
		        start: 0,
		        limit: 10
    	    },
    		proxy:{
    			url : '/user/',
    			timeout: 50000,
    	        api: {
    	            create:  '/user/create',
    	            read:    '/user/read',
    	            update:  '/user/update',
    	            destroy: '/user/destroy'
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
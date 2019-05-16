# sppinturasMobile
login: vini  
senha: senha  

## AC 07  
-SharedPreferences para lembrar login e ajuda na notificação simples  
-Todos dados são armazenados localmente, quando estiver offline  
-CRUD pedidos offline 
-Ler, editar e exluir offline  
-Existe um metodo que sincroniza quando der um get e o app estiver online  
-Notificação push: é disparada quando adicionado um orçamento na API. É disparada também quando é adicionado um pedido que não foi criado pelo App  
## OBS.  
Para a notificação push funcionar é necessário o TOKEN e a KEY. Api que está online está apontado para minha aplicação.  
App configurado para consumir api local.  
Link API local:https://github.com/viniciusoliveira95/ApiSppKotlin  

Para trocar o host basta ir no arquivo OrcamentoService.kt e trocar a string da variavel host por http://vinidev95.pythonanywhere.com  
GET lista de orcamentos: http://vinidev95.pythonanywhere.com/orcamentos  
GET lista de pedidos: http://vinidev95.pythonanywhere.com/pedidos  
  
OBS: Não é póssivel criar orçamento, pois é uma regra de negódcio

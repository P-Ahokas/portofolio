const db = require('../database');

const login={
  checkPassword: function(username, callback) {
      return db.query('SELECT pinkoodi FROM kortti WHERE korttinumero = ?',[username], callback); 
    }
};
          
module.exports = login;
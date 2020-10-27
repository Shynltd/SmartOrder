var mongoose = require('mongoose');
var tableSchema = new mongoose.Schema({
    number: {
        type: Number,
        required: true,
    },
    seats: {
        type: Number,
        required:true
    }
})
module.exports = mongoose.model('Table', tableSchema, 'table');
var books = [];

function findBook (bookId) {
    return books[findBookKey(bookId)];
}

function findBookKey (bookId) {
    for (var key = 0; key < books.length; key++) {
        if (books[key].id == bookId) {
            return key;
        }
    }
}

var bookService = {
    findAll(fn) {
        axios
            .get('/book')
            .then(response => fn(response))
    .catch(error => console.log(error))
    },

    findById(id, fn) {
        axios
            .get('/book/' + id)
            .then(response => fn(response))
    .catch(error => console.log(error))
    },

    create(book, fn) {
        axios
            .post('/book', book)
            .then(response => {
                alert("Book created successfully");
                fn(response)})
    .catch(error =>  {
        console.log(error)
        alert('This book already added!');
    })
    },

    update(id, book, fn) {
        axios
            .put('/book/' + id, book)
            .then(response => {
                alert("Book updated successfully");
                fn(response)})
    .catch(error => console.log(error))
    },

    deleteBook(id, fn) {
        axios
            .delete('/book/' + id)
            .then(response => {
                alert("Book deleted successfully");
                fn(response)})
    .catch(error => console.log(error))
    },

    getChapter(id, fn){
        axios
            .get('/book/'+id+'/chapter/')
            .then(response =>{ fn(response);
            console.log(response)})
            .catch(error => console.log(error))
    },

    getPageNumber(id, chapterNumber, fn){
        axios
            .get('/book/'+id+'/chapter/'+chapterNumber+'/page/')
            .then(response =>{ fn(response);
            console.log(response)})
            .catch(error => console.log(error))
    },
    readPage(id, chapterNumber, pageNumber,fn){
        axios
            .get('/book/'+id+'/chapter/'+chapterNumber+'page'+pageNumber)
            .then(response =>{ fn(response);
            console.log(response)})
            .catch(error => console.log(error))
    }

}

var List = Vue.extend({
    template: '#book-list',
    data: function () {
        return {books: [], searchKey: ''};
    },
    computed: {
        filteredBooks() {
            return this.books.filter((book) => {
                return book.name.indexOf(this.searchKey) > -1
                    || book.author.indexOf(this.searchKey) > -1
                    || book.pageNum.toString().indexOf(this.searchKey) > -1
            })
        }
    },
    mounted() {
        bookService.findAll(r => {this.books = r.data; books = r.data})
    }
});

var Book = Vue.extend({
    template: '#book',
    data: function () {
        return {book: findBook(this.$route.params.book_id)};
    }
});

var AddBook = Vue.extend({
    template: '#add-book',
    data() {
        return {
            book: {name: '', author: '', pageNum: 0, chapterNumber:0}
        }
    },
    methods: {
        createBook() {
            bookService.create(this.book, r => router.push('/'))
        }
    }
});

var BookEdit = Vue.extend({
    template: '#book-edit',
    data: function () {
        return {book: findBook(this.$route.params.book_id)};
    },
    methods: {
        updateBook: function () {
            bookService.update(this.book.id, this.book, r => router.push('/'))
        }
    }
});

var BookDelete = Vue.extend({
    template: '#book-delete',
    data: function () {
        return {book: findBook(this.$route.params.book_id)};
    },
    methods: {
        deleteBook: function () {
            bookService.deleteBook(this.book.id, r => router.push('/'))
        }
    }
});

var BookChapter = Vue.extend({
    template: '#book-chapter',
    data: function () {
        return{book: findBook(this.$route.params.book_id),bookChapterNumber:100};
    },
    mounted() {

            bookService.getChapter(this.book.id, r=>this.bookChapterNumber = r.data)

    }
});

var BookPage = Vue.extend({
    template: '#book-page',
    data: function () {
        console.log("CHAPTER NUMBER IS COMMING: "+this.$route.params.chapter_number);
        return{book: findBook(this.$route.params.book_id), bookChapterNumber:this.$route.params.chapter_number,bookPageNumber:0};
    },
    mounted() {
        bookService.getPageNumber(this.book.id, this.bookChapterNumber, r=>this.bookPageNumber = r.data )
    }
});

var ReadPage = Vue.extend({
    template: '#read-page',
    data: function () {
        return{book: findBook(this.$route.params.book_id), bookChapterNumber: this.$route.params.chapter_number, bookPageNumber:this.$route.params.page_number}
    },
    mounted() {
        bookService.readPage(this.book.id,this.bookChapterNumber,this.bookPageNumber,r => router.push('/'))
    }
});


var router = new VueRouter({
    routes: [
        {path: '/', component: List},
        {path: '/book/:book_id', component: Book, name: 'book'},
        {path: '/add-book', component: AddBook},
        {path: '/book/:book_id/edit', component: BookEdit, name: 'book-edit'},
        {path: '/book/:book_id/delete', component: BookDelete, name: 'book-delete'},
        {path: '/book/:book_id/chapter', component: BookChapter, name: 'book-chapter'},
        {path: '/book/:book_id/chapter/:chapter_number/page', component: BookPage, name: 'book-page'},
        {path: '/book/:book_id/chapter/:chapter_number/page/:page_number', component: ReadPage, name: 'read-page'}

    ]
});

new Vue({
    router
}).$mount('#app')
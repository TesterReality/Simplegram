<template>
    <div id="app">
        <div id="nav">
            <router-link to="/">Home</router-link>
            |
            <router-link to="/about">About</router-link>

            <v-btn @click="logOut">Выйти</v-btn>

        </div>
        <router-view/>
    </div>
</template>

<script>
    export default {
        name: 'Home',
        computed: {
            loggedIn() {
                return this.$store.state.auth.status.loggedIn;
            }

        },
        created() {
            if (this.loggedIn) {
                //this.$router.push('/about');
            }else
            {
                this.$router.push('/login');
            }
        },
        methods: {
            logOut() {
                this.$store.dispatch('auth/logout');
                this.$router.push('/login');
            }
        }
    };
</script>

<style lang="scss">
    #app {
        font-family: Avenir, Helvetica, Arial, sans-serif;
        -webkit-font-smoothing: antialiased;
        -moz-osx-font-smoothing: grayscale;
        text-align: center;
        color: #2c3e50;
    }

    #nav {
        padding: 30px;

        a {
            font-weight: bold;
            color: #2c3e50;

            &.router-link-exact-active {
                color: #42b983;
            }
        }
    }
</style>

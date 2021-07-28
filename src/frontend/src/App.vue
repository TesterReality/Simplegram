<template>
    <v-app>
        <v-main>
            <router-view></router-view>
        </v-main>
    </v-app>
          <!--  <v-btn @click="logOut">Выйти</v-btn>-->

</template>

<script>

    export default {
        name: 'App',
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

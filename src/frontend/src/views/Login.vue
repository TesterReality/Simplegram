<template>
  <v-app>
    <v-main>
      <v-container class="fill-height" fluid>
        <v-row align="center" justify="center" dense>
          <v-col cols="12" sm="8" md="4" lg="4">
            <v-card elevation="0">
              <a href="https://github.com/TesterReality/Simplegram" name="Simplegram" title="Simplegram" target="_blank">
                <v-img src="@/assets/simplegram.png" alt="Simplegram Logo" contain height="200"></v-img>
              </a>
              <v-card-text>
                <v-form>
                  <v-text-field label="Введите логин" name="login" prepend-inner-icon="mdi-account" type="login" class="rounded-lg" outlined></v-text-field>
                  <v-text-field label="Введите пароль" name="password" prepend-inner-icon="mdi-lock" type="password" class="rounded-lg" outlined></v-text-field>
                  <v-btn class="rounded-lg" color="#000000" x-large block dark>Войти</v-btn>
                  <v-card-actions class="text--secondary" align="center">
                    <!-- <router-link :to="{ name: 'SignUp' }">Sign Up</router-link> -->
                    <v-card-text>
                    Нет аккаунта? <a href="/registration" class="pl-2" style="color: #000000">Зарегистрироваться</a>
                    </v-card-text>
                  </v-card-actions>
                </v-form>
              </v-card-text>
            </v-card>
          </v-col>
        </v-row>
      </v-container>
    </v-main>
  </v-app>
</template>

<script>

</script>

<style lang="css" scoped>

</style>
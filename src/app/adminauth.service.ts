import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class AdminauthService {
  constructor() {}

  authenticate(username2: string, password2: string) {
    if (username2 === 'admin' && password2 === 'admin123') {
      sessionStorage.setItem('username2', username2);
      return true;
    } else {
      return false;
    }
  }

  isUserLoggedIn() {
    let user = sessionStorage.getItem('username2');
    console.log(!(user === null));
    return !(user === null);
  }

  logOut() {
    sessionStorage.removeItem('username2');
  }
}

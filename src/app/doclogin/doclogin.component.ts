import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from '../authentication.service';

@Component({
  selector: 'app-doclogin',
  templateUrl: './doclogin.component.html'
})
export class DocloginComponent implements OnInit {

  username = ''
  password = ''
  invalidLogin = false
  loading = false

  constructor(private router:Router, public loginservice: AuthenticationService) { }

  ngOnInit(): void {
  }

  checkLogin() {
    this.loading = true;
    this.loginservice.loginDoctor(this.username, this.password).subscribe({
      next: (res) => {
        console.log("Login successful", res);
        this.loading = false;
        this.invalidLogin = false;
        this.router.navigate(['docdash']);
      },
      error: (err) => {
        console.error("Login failed", err);
        this.loading = false;
        this.invalidLogin = true;
      }
    });
  }

}

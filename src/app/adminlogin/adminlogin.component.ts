import { Component, OnInit } from '@angular/core';
import { AdminauthService } from '../adminauth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-adminlogin',
  templateUrl: './adminlogin.component.html'
})
export class AdminloginComponent implements OnInit {

  username2 = ''
  password2 = ''
  invalidLogin = false
  loading = false

  constructor(private router:Router, public loginservice: AdminauthService) { }

  ngOnInit(): void {
  }

  checkLogin() {
    this.loading = true;
    this.loginservice.authenticate(this.username2, this.password2).subscribe({
      next: (res) => {
        console.log("Admin Login successful", res);
        this.loading = false;
        this.invalidLogin = false;
        this.router.navigate(['admindash']);
      },
      error: (err) => {
        console.error("Admin Login failed", err);
        this.loading = false;
        this.invalidLogin = true;
      }
    });
  }

}

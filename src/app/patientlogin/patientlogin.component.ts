import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from '../authentication.service';

@Component({
  selector: 'app-patientlogin',
  templateUrl: './patientlogin.component.html',
  styleUrls: ['./patientlogin.component.css']
})
export class PatientloginComponent implements OnInit {

  email = '';
  password = '';
  invalidLogin = false;

  constructor(private router: Router, private loginservice: AuthenticationService) { }

  ngOnInit(): void {
  }

  checkLogin() {
    this.loginservice.loginPatient(this.email, this.password).subscribe({
      next: (res) => {
        console.log("Patient Login successful", res);
        this.invalidLogin = false;
        this.router.navigate(['home']); // Navigate to home or patient dashboard
      },
      error: (err) => {
        console.error("Patient Login failed", err);
        this.invalidLogin = true;
        alert("Wrong Patient Credentials or Server error");
      }
    });
  }

}

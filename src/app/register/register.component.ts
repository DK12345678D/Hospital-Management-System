import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from '../authentication.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html'
})
export class RegisterComponent implements OnInit {

  user = {
    fullName: '',
    email: '',
    phoneNumber: '',
    password: '',
    confirmPassword: '',
    role: 'PATIENT'
  };

  constructor(private router: Router, private authService: AuthenticationService) { }

  ngOnInit(): void {
  }

  onRegister() {
    if (this.user.password !== this.user.confirmPassword) {
      alert("Passwords do not match!");
      return;
    }

    if (this.user.role === 'PATIENT') {
      this.authService.registerPatient(this.user).subscribe(this.handleResponse());
    } else if (this.user.role === 'DOCTOR') {
      this.authService.registerDoctor(this.user).subscribe(this.handleResponse());
    } else if (this.user.role === 'ADMIN') {
      this.authService.registerAdmin(this.user).subscribe(this.handleResponse());
    }
  }

  private handleResponse() {
    return {
      next: (res: any) => {
        alert("Registration Successful!");
        this.router.navigate([this.getLoginLink()]);
      },
      error: (err: any) => {
        console.error("Registration failed", err);
        alert("Registration failed: " + (err.error?.message || "Unknown error"));
      }
    };
  }

  private getLoginLink() {
    if (this.user.role === 'ADMIN') return 'adlogin';
    if (this.user.role === 'DOCTOR') return 'doclogin';
    return 'patientlogin';
  }
}

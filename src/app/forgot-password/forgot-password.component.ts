import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from '../authentication.service';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent implements OnInit {

  email = '';

  constructor(private router: Router, private authService: AuthenticationService) { }

  ngOnInit(): void {
  }

  onSubmit() {
    this.authService.forgotPassword(this.email).subscribe({
      next: (res) => {
        alert("Rest OTP sent to your registered contact!");
        this.router.navigate(['reset-password'], { queryParams: { email: this.email } });
      },
      error: (err) => {
        alert("Error: " + (err.error?.message || "User not found"));
      }
    });
  }

}

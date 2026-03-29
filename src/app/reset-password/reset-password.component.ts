import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthenticationService } from '../authentication.service';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.css']
})
export class ResetPasswordComponent implements OnInit {

  data = {
    identifier: '',
    otp: '',
    newPassword: '',
    confirmPassword: ''
  };

  constructor(private route: ActivatedRoute, private router: Router, private authService: AuthenticationService) { }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.data.identifier = params['email'] || '';
    });
  }

  onSubmit() {
    if (this.data.newPassword !== this.data.confirmPassword) {
      alert("Passwords do not match!");
      return;
    }

    this.authService.resetPassword(this.data).subscribe({
      next: (res) => {
        alert("Password reset successfully!");
        this.router.navigate(['doclogin']); // Or home
      },
      error: (err) => {
        alert("Error: " + (err.error?.message || "Invalid OTP or request"));
      }
    });
  }

}

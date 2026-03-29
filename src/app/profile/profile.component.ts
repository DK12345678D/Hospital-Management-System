import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../authentication.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  user = {
    fullName: '',
    phoneNumber: '',
    email: sessionStorage.getItem('email') || ''
  };
  
  loading = false;
  successMessage = '';
  errorMessage = '';

  constructor(private authService: AuthenticationService) { }

  ngOnInit(): void {
    // Initial fetch of profile could be done here if backend had a getProfile
    // For now, we only implement the update part as per backend addition.
  }

  onUpdate() {
    this.loading = true;
    this.successMessage = '';
    this.errorMessage = '';

    this.authService.updateProfile(this.user).subscribe({
      next: (res) => {
        this.loading = false;
        this.successMessage = "Profile updated successfully!";
        console.log("Profile updated", res);
      },
      error: (err) => {
        this.loading = false;
        this.errorMessage = err.error?.message || "Failed to update profile";
        console.error("Update failed", err);
      }
    });
  }

}

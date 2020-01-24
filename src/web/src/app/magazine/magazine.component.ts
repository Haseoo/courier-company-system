import { MagazineView } from '../model/views/magazineView';
import { Router, ActivatedRoute } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { MagazineService } from '../services/magazine.service';

@Component({
  templateUrl: './magazine.component.html',
  styleUrls: ['./magazine.component.css']
})
export class MagazineComponent implements OnInit {

  magazine: MagazineView;

  constructor(private router: Router,
              private magazineService: MagazineService,
              private route: ActivatedRoute) { }
  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    this.magazineService.getById(Number(id)).subscribe(res => {
      this.magazine = res;
    });
  }
  gotoMagazines() {
    this.router.navigate(['/admin/magazines']);
  }

}

import { ImdbportalPage } from './app.po';

describe('imdbportal App', function() {
  let page: ImdbportalPage;

  beforeEach(() => {
    page = new ImdbportalPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});

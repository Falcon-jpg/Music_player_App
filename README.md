### Music Player APP

Welcome to our Music Player App, where your favorite tunes are just a click away! Powered by Retrofit, our app seamlessly integrates with the Deezer API from Rapid API, offering a vast library of songs at your fingertips.

With a sleek and intuitive interface, users can effortlessly search for their beloved artists or tracks. But that's just the beginning! Our app goes beyond mere playback - users have the power to curate their personal playlists by liking songs, a feature seamlessly managed through SQLite integration.

In the Home fragment, users can revisit their liked songs, creating a musical journey that evolves with their tastes. And when the urge to immerse oneself in melody strikes, a simple tap on any song transforms the screen into a dynamic media player, ready to serenade you with your chosen tunes.

Experience the joy of music like never before with our Music Player App. Your playlist, your way - start listening today!

#### Home Fragment

The Home fragment displays all the liked songs. Liked songs are stored in a SQLite database.

![Home Fragment](https://github.com/Falcon-jpg/Music_player_App/assets/109679302/12e56637-7734-4bf2-8f12-07e7d7c78051)

Users can unlike a song by clicking on the heart button.

![Unlike Song](https://github.com/Falcon-jpg/Music_player_App/assets/109679302/484e989a-a564-4988-b8aa-c2c77f22efd4)

#### Search Fragment

The Search fragment contains a search box where users can search for an artist or song. A shimmer layout is displayed until the data is fetched via Retrofit.

![Search Fragment](https://github.com/Falcon-jpg/Music_player_App/assets/109679302/6baed04e-f8aa-410e-aec2-240bf13b98fe)

The fetched song list is displayed using a RecyclerView. Users can like/dislike songs here.

![Song List](https://github.com/Falcon-jpg/Music_player_App/assets/109679302/7c1ffa4e-ddc7-4c9c-bc9a-a74e063fbccd)

#### Song Details

Each song displays its liked status. If liked, the status is set to **true**.

![Liked Song](https://github.com/Falcon-jpg/Music_player_App/assets/109679302/03056702-7e3c-4e5d-9b26-73b81cdf8e2c)

#### Media Player

Clicking on a song from the Search or Home fragment opens the media player, where users can listen to the selected song.

![Media Player](https://github.com/Falcon-jpg/Music_player_App/assets/109679302/3c19bf06-4007-4876-bcdb-8699ac09087f)




-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 17 Jun 2026 pada 11.05
-- Versi server: 10.4.32-MariaDB
-- Versi PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `neoyomi`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `contents`
--

CREATE TABLE `contents` (
  `id` varchar(500) NOT NULL,
  `judul` varchar(255) NOT NULL,
  `content_type` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Struktur dari tabel `genres`
--

CREATE TABLE `genres` (
  `id` int(11) NOT NULL,
  `nama_genre` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data untuk tabel `genres`
--

INSERT INTO `genres` (`id`, `nama_genre`) VALUES
(1, 'Action'),
(2, 'Adventure'),
(34, 'Anti-Hero'),
(3, 'Comedy'),
(26, 'Cultivation'),
(4, 'Drama'),
(32, 'Dungeon'),
(37, 'Dystopian'),
(15, 'Ecchi'),
(5, 'Fantasy'),
(31, 'Gate'),
(16, 'Harem'),
(8, 'Horror'),
(30, 'Hunter'),
(24, 'Isekai'),
(23, 'Josei'),
(40, 'Magic'),
(25, 'Martial Arts'),
(17, 'Mecha'),
(18, 'Music'),
(9, 'Mystery'),
(36, 'Post-Apocalyptic'),
(10, 'Psychological'),
(28, 'Regression'),
(6, 'Romance'),
(19, 'School'),
(11, 'Sci-Fi'),
(20, 'Seinen'),
(22, 'Shoujo'),
(21, 'Shounen'),
(7, 'Slice of Life'),
(38, 'Space Opera'),
(14, 'Sports'),
(33, 'Superhero'),
(12, 'Supernatural'),
(27, 'System'),
(13, 'Thriller'),
(39, 'Time Travel'),
(29, 'Tower'),
(35, 'Villain');

-- --------------------------------------------------------

--
-- Struktur dari tabel `komik`
--

CREATE TABLE `komik` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `lang` varchar(10) NOT NULL DEFAULT 'en',
  `type` varchar(20) NOT NULL DEFAULT 'manga',
  `version` varchar(50) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL,
  `jenis` varchar(50) DEFAULT NULL,
  `publisher` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data untuk tabel `komik`
--

INSERT INTO `komik` (`id`, `name`, `lang`, `type`, `version`, `url`, `jenis`, `publisher`) VALUES
(1, 'MangaDex', 'en', 'manga', '1.0.0', 'https://mangadex.org', NULL, NULL),
(2, 'Komiku', 'id', 'manga', '1.0.0', 'https://komiku.id', NULL, NULL),
(3, 'MangaPlus', 'en', 'manga', '1.0.0', 'https://mangaplus.shueisha.co.jp', NULL, NULL),
(4, 'Webtoon', 'en', 'manhwa', '1.0.0', 'https://www.webtoons.com', NULL, NULL),
(5, 'TakoManga', 'id', 'manhwa', '1.0.0', 'https://takomanga.com', NULL, NULL),
(6, 'MangaToon', 'en', 'manhua', '1.0.0', 'https://mangatoon.mobi', NULL, NULL),
(7, 'QiManga', 'en', 'manhua', '1.0.0', 'https://qimanga.com', NULL, NULL),
(8, 'Marvel Unlimited', 'en', 'comics', '1.0.0', 'https://www.marvel.com/comics/unlimited', NULL, NULL),
(9, 'DC Universe', 'en', 'comics', '1.0.0', 'https://www.dcuniverse.com', NULL, NULL),
(10, 'ComiXology', 'en', 'comics', '1.0.0', 'https://www.comixology.com', NULL, NULL);

-- --------------------------------------------------------

--
-- Struktur dari tabel `komik_backup`
--

CREATE TABLE `komik_backup` (
  `id` int(11) NOT NULL DEFAULT 0,
  `name` varchar(255) NOT NULL,
  `lang` varchar(10) NOT NULL DEFAULT 'en',
  `type` varchar(20) NOT NULL DEFAULT 'manga',
  `version` varchar(50) DEFAULT NULL,
  `url` varchar(500) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data untuk tabel `komik_backup`
--

INSERT INTO `komik_backup` (`id`, `name`, `lang`, `type`, `version`, `url`) VALUES
(1, 'MangaDex', 'en', 'manga', '1.0.0', 'https://mangadex.org'),
(2, 'Komiku', 'id', 'manga', '1.0.0', 'https://komiku.id'),
(3, 'MangaPlus', 'en', 'manga', '1.0.0', 'https://mangaplus.shueisha.co.jp'),
(4, 'Webtoon', 'en', 'manhwa', '1.0.0', 'https://www.webtoons.com'),
(5, 'TakoManga', 'id', 'manhwa', '1.0.0', 'https://takomanga.com'),
(6, 'MangaToon', 'en', 'manhua', '1.0.0', 'https://mangatoon.mobi'),
(7, 'QiManga', 'en', 'manhua', '1.0.0', 'https://qimanga.com'),
(8, 'Marvel Unlimited', 'en', 'comics', '1.0.0', 'https://www.marvel.com/comics/unlimited'),
(9, 'DC Universe', 'en', 'comics', '1.0.0', 'https://www.dcuniverse.com'),
(10, 'ComiXology', 'en', 'comics', '1.0.0', 'https://www.comixology.com');

-- --------------------------------------------------------

--
-- Struktur dari tabel `komik_genres`
--

CREATE TABLE `komik_genres` (
  `komik_id` int(11) NOT NULL,
  `genre_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data untuk tabel `komik_genres`
--

INSERT INTO `komik_genres` (`komik_id`, `genre_id`) VALUES
(1, 1),
(1, 5),
(3, 1),
(3, 2),
(3, 21),
(4, 4),
(4, 5),
(4, 6),
(4, 7),
(5, 1),
(5, 27),
(5, 28),
(5, 30),
(6, 5),
(6, 6),
(6, 25),
(6, 26),
(7, 1),
(7, 25),
(7, 26),
(8, 1),
(8, 11),
(8, 33),
(8, 34),
(9, 1),
(9, 33),
(9, 35),
(9, 36),
(10, 8),
(10, 11),
(10, 13),
(10, 33);

-- --------------------------------------------------------

--
-- Struktur dari tabel `novel`
--

CREATE TABLE `novel` (
  `id` varchar(50) NOT NULL,
  `name` varchar(255) NOT NULL,
  `site` varchar(255) DEFAULT NULL,
  `lang` varchar(10) NOT NULL DEFAULT 'en',
  `url` varchar(500) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data untuk tabel `novel`
--

INSERT INTO `novel` (`id`, `name`, `site`, `lang`, `url`) VALUES
('novel-001', 'NovelUpdates', 'NovelUpdates', 'en', 'https://www.novelupdates.com');

-- --------------------------------------------------------

--
-- Struktur dari tabel `novel_genres`
--

CREATE TABLE `novel_genres` (
  `novel_id` varchar(50) NOT NULL,
  `genre_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data untuk tabel `novel_genres`
--

INSERT INTO `novel_genres` (`novel_id`, `genre_id`) VALUES
('novel-001', 4),
('novel-001', 6);

-- --------------------------------------------------------

--
-- Struktur dari tabel `reading_history`
--

CREATE TABLE `reading_history` (
  `id` int(11) NOT NULL,
  `manga_id` varchar(500) NOT NULL,
  `judul` varchar(255) NOT NULL,
  `gambar_sampul` text DEFAULT NULL,
  `tags` varchar(500) DEFAULT NULL,
  `type` varchar(20) NOT NULL DEFAULT 'manga',
  `waktu_baca` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data untuk tabel `reading_history`
--

INSERT INTO `reading_history` (`id`, `manga_id`, `judul`, `gambar_sampul`, `tags`, `type`, `waktu_baca`) VALUES
(1, '2', 'Berserk', 'https://cdn.myanimelist.net/images/manga/1/157897l.jpg', 'Action,Adventure,Award Winning,Drama,Fantasy,Horror', 'manga', '2026-06-17 07:57:08'),
(2, '116778', 'Chainsaw Man', 'https://cdn.myanimelist.net/images/manga/3/216464l.jpg', 'Action,Award Winning,Fantasy', 'manga', '2026-06-17 06:58:28'),
(4, '11', 'Naruto', 'https://cdn.myanimelist.net/images/manga/3/249658l.jpg', 'Action,Adventure,Fantasy', 'manga', '2026-06-17 07:21:34'),
(9, '1', 'Monster', 'https://cdn.myanimelist.net/images/manga/3/258224l.jpg', 'Award Winning,Drama,Mystery', 'manga', '2026-06-16 13:32:47'),
(10, '121496', 'Solo Leveling', 'https://cdn.myanimelist.net/images/manga/3/222295l.jpg', 'Action,Adventure,Fantasy', 'manga', '2026-06-14 04:54:35'),
(16, '43115', 'Going Otome', 'https://cdn.myanimelist.net/images/manga/2/75837l.jpg', 'Hentai', 'manga', '2026-06-16 13:32:43'),
(32, '12', 'Bleach', 'https://cdn.myanimelist.net/images/manga/3/180031l.jpg', 'Action,Adventure,Award Winning,Supernatural', 'manga', '2026-06-17 07:57:13'),
(41, '18', 'Kodomo no Omocha', 'https://cdn.myanimelist.net/images/manga/1/267715l.jpg', 'Award Winning,Comedy,Drama,Romance,Slice of Life', 'manga', '2026-06-17 07:20:34'),
(43, '7', 'Hajime no Ippo', 'https://cdn.myanimelist.net/images/manga/2/250313l.jpg', 'Award Winning,Sports', 'manga', '2026-06-17 07:20:42');

-- --------------------------------------------------------

--
-- Struktur dari tabel `tags`
--

CREATE TABLE `tags` (
  `content_id` varchar(500) NOT NULL,
  `tag` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Struktur dari tabel `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `username` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` enum('user','admin') NOT NULL DEFAULT 'user',
  `dibuat_pada` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data untuk tabel `users`
--

INSERT INTO `users` (`id`, `username`, `password`, `role`, `dibuat_pada`) VALUES
(1, 'admin', 'admin123', 'admin', '2026-06-17 08:41:10');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `contents`
--
ALTER TABLE `contents`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `genres`
--
ALTER TABLE `genres`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `nama_genre` (`nama_genre`);

--
-- Indeks untuk tabel `komik`
--
ALTER TABLE `komik`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `komik_genres`
--
ALTER TABLE `komik_genres`
  ADD PRIMARY KEY (`komik_id`,`genre_id`),
  ADD KEY `genre_id` (`genre_id`);

--
-- Indeks untuk tabel `novel`
--
ALTER TABLE `novel`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `novel_genres`
--
ALTER TABLE `novel_genres`
  ADD PRIMARY KEY (`novel_id`,`genre_id`),
  ADD KEY `genre_id` (`genre_id`);

--
-- Indeks untuk tabel `reading_history`
--
ALTER TABLE `reading_history`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `manga_id` (`manga_id`(255));

--
-- Indeks untuk tabel `tags`
--
ALTER TABLE `tags`
  ADD PRIMARY KEY (`content_id`,`tag`);

--
-- Indeks untuk tabel `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `genres`
--
ALTER TABLE `genres`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=48;

--
-- AUTO_INCREMENT untuk tabel `komik`
--
ALTER TABLE `komik`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT untuk tabel `reading_history`
--
ALTER TABLE `reading_history`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=51;

--
-- AUTO_INCREMENT untuk tabel `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `komik_genres`
--
ALTER TABLE `komik_genres`
  ADD CONSTRAINT `komik_genres_ibfk_1` FOREIGN KEY (`komik_id`) REFERENCES `komik` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `komik_genres_ibfk_2` FOREIGN KEY (`genre_id`) REFERENCES `genres` (`id`) ON DELETE CASCADE;

--
-- Ketidakleluasaan untuk tabel `novel_genres`
--
ALTER TABLE `novel_genres`
  ADD CONSTRAINT `novel_genres_ibfk_1` FOREIGN KEY (`novel_id`) REFERENCES `novel` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `novel_genres_ibfk_2` FOREIGN KEY (`genre_id`) REFERENCES `genres` (`id`) ON DELETE CASCADE;

--
-- Ketidakleluasaan untuk tabel `tags`
--
ALTER TABLE `tags`
  ADD CONSTRAINT `tags_ibfk_1` FOREIGN KEY (`content_id`) REFERENCES `contents` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
